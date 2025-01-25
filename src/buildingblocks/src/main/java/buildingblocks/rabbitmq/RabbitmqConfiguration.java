package buildingblocks.rabbitmq;

import buildingblocks.outboxprocessor.MessageDeliveryType;
import buildingblocks.outboxprocessor.PersistMessageEntity;
import buildingblocks.outboxprocessor.PersistMessageProcessor;
import buildingblocks.utils.reflection.ReflectionUtils;
import org.slf4j.Logger;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.AsyncRabbitTemplate;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.listener.MessageListenerContainer;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.amqp.RabbitProperties;
import org.springframework.context.annotation.*;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.support.TransactionTemplate;

import java.util.HashMap;
import java.util.UUID;
import java.util.function.Supplier;

@Configuration
public class RabbitmqConfiguration {

  @Value("${spring.rabbitmq.template.exchange-type}")
  private String exchangeType;

  private final RabbitProperties rabbitProperties;
  private final TransactionTemplate transactionTemplate;
  private final Logger logger;

  public RabbitmqConfiguration(
    RabbitProperties rabbitProperties,
    PlatformTransactionManager platformTransactionManager,
    Logger logger) {
    this.rabbitProperties = rabbitProperties;
    this.transactionTemplate = new TransactionTemplate(platformTransactionManager);
    this.logger = logger;
  }


  @Bean
  public ConnectionFactory connectionFactory() {
    CachingConnectionFactory connectionFactory = new CachingConnectionFactory(rabbitProperties.getHost());
    connectionFactory.setPort(rabbitProperties.getPort());
    connectionFactory.setUsername(rabbitProperties.getUsername());
    connectionFactory.setPassword(rabbitProperties.getPassword());
    return connectionFactory;
  }

  @Bean
  public RabbitAdmin rabbitAdmin(ConnectionFactory connectionFactory) {
   return new RabbitAdmin(connectionFactory);
  }

//  public String getQueueName() {
//    return rabbitProperties.getTemplate().getExchange() + "_queue";
//  }

//  @Bean
//  public Queue queue() {
//    return new Queue(rabbitProperties.getTemplate().getExchange() + "_queue", true);
//  }
//
//  @Bean
//  public Exchange exchange() {
//    return switch (exchangeType.toLowerCase()) {
//      case "direct" -> new DirectExchange(rabbitProperties.getTemplate().getExchange());
//      case "fanout" -> new FanoutExchange(rabbitProperties.getTemplate().getExchange());
//      default -> new TopicExchange(rabbitProperties.getTemplate().getExchange());
//    };
//  }
//
//
//  @Bean
//  public Binding binding(Queue queue, Exchange exchange) {
//    String routingKey = rabbitProperties.getTemplate().getExchange() + "_routing_key";
//    return switch (exchange) {
//      case TopicExchange topicExchange -> BindingBuilder.bind(queue).to(topicExchange).with(routingKey);
//      case DirectExchange directExchange -> BindingBuilder.bind(queue).to(directExchange).with(routingKey);
//      case FanoutExchange fanoutExchange -> BindingBuilder.bind(queue).to(fanoutExchange);
//      case null, default -> throw new IllegalArgumentException("Unsupported exchange type for binding");
//    };
//  }

  @Bean
  public AsyncRabbitTemplate asyncTemplate(ConnectionFactory connectionFactory) {
    return new AsyncRabbitTemplate(new RabbitTemplate(connectionFactory));
  }

  @Bean
  public RabbitTemplate template(ConnectionFactory connectionFactory) {
    return new RabbitTemplate(connectionFactory);
  }

  @Bean
  public MessageListenerContainer addListeners(PersistMessageProcessor persistMessageProcessor, RabbitAdmin rabbitAdmin) {
    SimpleMessageListenerContainer container = new SimpleMessageListenerContainer();
    container.setConnectionFactory(connectionFactory());

//    // Change to support multiple listeners
//    List<MessageListener> listeners = ReflectionUtils.getAllInstanceOfSubclasses(MessageListener.class);
//

    // Map to store message type to listener mapping
    HashMap<Class<?>, Supplier<MessageListener>> listenerMap = new HashMap<>();

    // Find all MessageListener implementations
    var listener = ReflectionUtils.getInstanceOfSubclass(MessageListener.class);
    if (listener != null) {
      RabbitmqMessageHandler annotation = listener.getClass().getAnnotation(RabbitmqMessageHandler.class);

      if (annotation != null) {
        var typeName = annotation.messageType().getTypeName();

        DirectExchange directExchange = new DirectExchange(typeName);
        rabbitAdmin.declareExchange(directExchange);

        Queue queue = new Queue(typeName + "_queue", true);
        rabbitAdmin.declareQueue(queue);

        // Bind the queue to the fanout exchange
        rabbitAdmin.declareBinding(BindingBuilder.bind(queue).to(directExchange).with(typeName));

        // Set the queue name from the annotation
        container.setQueueNames(queue.getName());

        // Store a supplier for the listener
        listenerMap.put(annotation.messageType(), () -> listener);
      }

      // Set the message listener
      container.setMessageListener(message -> {
        transactionTemplate.execute(status -> {
          try {
            UUID id = persistMessageProcessor.addReceivedMessage(message);
            PersistMessageEntity persistMessage = persistMessageProcessor.existInboxMessage(id);

            if (persistMessage == null) {

              Class<?> messageType = ReflectionUtils.findClassFromName(message.getMessageProperties().getType());

              // Find the appropriate handler
              Supplier<MessageListener> handlerSupplier = listenerMap.get(messageType);

              if (handlerSupplier != null) {
                MessageListener handler = handlerSupplier.get();
                handler.onMessage(message);
                persistMessageProcessor.process(id, MessageDeliveryType.Inbox);
              } else {
                logger.warn("No handler found for message type: {}", messageType.getTypeName());
              }
            }
          } catch (Exception ex) {
            status.setRollbackOnly();
            throw ex;
          }
          return null;
        });
      });
    }
    return container;
  }
}
