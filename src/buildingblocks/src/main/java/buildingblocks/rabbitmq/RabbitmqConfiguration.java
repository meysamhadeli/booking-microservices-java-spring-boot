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
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.*;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.support.TransactionTemplate;
import java.util.HashMap;
import java.util.Map;
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

  @Bean
  public AsyncRabbitTemplate asyncTemplate(ConnectionFactory connectionFactory) {
    return new AsyncRabbitTemplate(new RabbitTemplate(connectionFactory));
  }

  @Bean
  public RabbitTemplate template(ConnectionFactory connectionFactory) {
    return new RabbitTemplate(connectionFactory);
  }

  @Bean
  public MessageListenerContainer addListeners(
    PersistMessageProcessor persistMessageProcessor,
    RabbitAdmin rabbitAdmin,
    ApplicationContext applicationContext) {
    SimpleMessageListenerContainer container = new SimpleMessageListenerContainer();
    container.setConnectionFactory(connectionFactory());
    container.setAcknowledgeMode(AcknowledgeMode.AUTO);

    // Map to store message type to listener mapping
    HashMap<Class<?>, Supplier<MessageHandler<?>>> listenerMap = new HashMap<>();

    // Retrieve all beans of type MessageHandler
    Map<String, MessageHandler> listeners = applicationContext.getBeansOfType(MessageHandler.class);

    listeners.values().forEach(listener -> {
      // Infer the message type from the listener instance
      Class<?> messageType = ReflectionUtils.getGenericTypeParameter(listener.getClass(), MessageHandler.class);
      String typeName = messageType.getTypeName();

      // Declare queue, exchange, and bindings
      Queue queue = declareQueue(rabbitAdmin, typeName);
      Exchange exchange = declareExchange(rabbitAdmin);
      declareBindings(rabbitAdmin, queue, exchange, typeName);

      // Add the queue to the container
      container.setQueueNames(queue.getName());

      // Store the listener in the map
      listenerMap.put(messageType, () -> listener);
    });

    // Set the message listener
    container.setMessageListener(message -> {
      transactionTemplate.execute(status -> {
        try {
          UUID id = persistMessageProcessor.addReceivedMessage(message);
          PersistMessageEntity persistMessage = persistMessageProcessor.existInboxMessage(id);

          if (persistMessage == null) {
            // Infer the message type from the message properties
            Class<?> messageType = ReflectionUtils.findClassFromName(message.getMessageProperties().getType());

            // Find the appropriate handler
            Supplier<MessageHandler<?>> handlerSupplier = listenerMap.get(messageType);

            if (handlerSupplier != null) {
              MessageHandler<?> handler = handlerSupplier.get();
              handler.onMessage(message); // This will delegate to the consume method
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

    return container;
  }

  private static void declareBindings(RabbitAdmin rabbitAdmin, Queue queue, Exchange exchange, String routingKey) {
    switch (exchange) {
      case TopicExchange topicExchange -> rabbitAdmin.declareBinding(BindingBuilder.bind(queue).to(topicExchange).with(routingKey));
      case DirectExchange directExchange -> rabbitAdmin.declareBinding(BindingBuilder.bind(queue).to(directExchange).with(routingKey));
      case FanoutExchange fanoutExchange -> rabbitAdmin.declareBinding(BindingBuilder.bind(queue).to(fanoutExchange));
      case null, default -> throw new IllegalArgumentException("Unsupported exchange type for binding");
    };
  }

  private Exchange declareExchange(RabbitAdmin rabbitAdmin) {
    Exchange exchange = switch (exchangeType.toLowerCase()) {
      case "direct" ->
        new DirectExchange(rabbitProperties.getTemplate().getExchange());
      case "fanout" ->
        new FanoutExchange(rabbitProperties.getTemplate().getExchange());
      default ->
        new TopicExchange(rabbitProperties.getTemplate().getExchange());
    };

    rabbitAdmin.declareExchange(exchange);

    return exchange;
  }

  private static Queue declareQueue(RabbitAdmin rabbitAdmin, String typeName) {
    Queue queue = new Queue(typeName + "_queue", true);
    rabbitAdmin.declareQueue(queue);
    return queue;
  }
}
