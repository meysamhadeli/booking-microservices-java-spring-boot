package buildingblocks.rabbitmq;

import buildingblocks.outboxprocessor.PersistMessageProcessor;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.AsyncRabbitTemplate;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.listener.MessageListenerContainer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.amqp.RabbitProperties;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
public class RabbitmqConfiguration {

    @Value("${spring.rabbitmq.template.exchange-type}")
    private String exchangeType;

    private final RabbitProperties rabbitProperties;

    public RabbitmqConfiguration(RabbitProperties rabbitProperties) {
        this.rabbitProperties = rabbitProperties;
    }

    @Bean
    public ConnectionFactory connectionFactory() {
        CachingConnectionFactory connectionFactory = new CachingConnectionFactory(rabbitProperties.getHost());
        connectionFactory.setPort(rabbitProperties.getPort());
        connectionFactory.setUsername(rabbitProperties.getUsername());
        connectionFactory.setPassword(rabbitProperties.getPassword());
        return connectionFactory;
    }

    public String getQueueName() {
        return rabbitProperties.getTemplate().getExchange() + "_queue";
    }

    @Bean
    public Queue queue() {
        return new Queue(rabbitProperties.getTemplate().getExchange() + "_queue", true);
    }

    @Bean
    public Exchange exchange() {
        return switch (exchangeType.toLowerCase()) {
            case "direct" -> new DirectExchange(rabbitProperties.getTemplate().getExchange());
            case "fanout" -> new FanoutExchange(rabbitProperties.getTemplate().getExchange());
            default -> new TopicExchange(rabbitProperties.getTemplate().getExchange());
        };
    }


    @Bean
    public Binding binding(Queue queue, Exchange exchange) {
        String routingKey = rabbitProperties.getTemplate().getExchange() + "_routing_key";
        return switch (exchange) {
            case TopicExchange topicExchange -> BindingBuilder.bind(queue).to(topicExchange).with(routingKey);
            case DirectExchange directExchange -> BindingBuilder.bind(queue).to(directExchange).with(routingKey);
            case FanoutExchange fanoutExchange -> BindingBuilder.bind(queue).to(fanoutExchange);
            case null, default -> throw new IllegalArgumentException("Unsupported exchange type for binding");
        };
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
    public RabbitmqPublisher rabbitmqPublisher(AsyncRabbitTemplate asyncRabbitTemplate, RabbitTemplate rabbitTemplate) {
        return new RabbitmqPublisherImpl(rabbitProperties, asyncRabbitTemplate, rabbitTemplate);
    }

    @Bean
    public RabbitmqReceiver rabbitmqReceiver(PersistMessageProcessor persistMessageProcessor, PlatformTransactionManager platformTransactionManager) {
        return new RabbitmqReceiverImpl(this, persistMessageProcessor, platformTransactionManager);
    }

    @Bean
    public MessageListenerContainer addListeners(RabbitmqReceiver rabbitmqReceiver) {
        return rabbitmqReceiver.addListeners();
    }
}