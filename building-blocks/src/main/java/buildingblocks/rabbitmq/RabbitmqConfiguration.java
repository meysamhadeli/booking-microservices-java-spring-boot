package buildingblocks.rabbitmq;

import buildingblocks.outboxprocessor.PersistMessageProcessor;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.AsyncRabbitTemplate;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.amqp.RabbitProperties;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingClass;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitmqConfiguration {

    @Value("${spring.rabbitmq.template.exchange-type}")
    private String exchangeType;

    private final RabbitProperties rabbitProperties;

    public RabbitmqConfiguration(RabbitProperties rabbitProperties) {
        this.rabbitProperties = rabbitProperties;
    }

    @Bean
    @ConditionalOnMissingBean
    public ConnectionFactory rabbitmqConnectionFactory() {
        CachingConnectionFactory connectionFactory = new CachingConnectionFactory(rabbitProperties.getHost());
        connectionFactory.setPort(rabbitProperties.getPort());
        connectionFactory.setUsername(rabbitProperties.getUsername());
        connectionFactory.setPassword(rabbitProperties.getPassword());
        return connectionFactory;
    }

    public String getQueueName() {
        return rabbitProperties.getTemplate().getExchange() + "_queue";
    }

    public String getRoutingKey() {
        return rabbitProperties.getTemplate().getExchange() + "_routing_key";
    }

    @Bean
    public Queue queue() {
        return new Queue(getQueueName(), true);
    }

    @Bean
    public Exchange exchange() {
        return switch (exchangeType.toLowerCase()) {
            case "direct" -> new DirectExchange(exchangeType);
            case "fanout" -> new FanoutExchange(exchangeType);
            default -> new TopicExchange(exchangeType);
        };
    }

    @Bean
    public Binding binding() {
        return switch (exchange()) {
            case TopicExchange topicExchange -> BindingBuilder.bind(queue()).to(topicExchange).with(getRoutingKey());
            case DirectExchange directExchange -> BindingBuilder.bind(queue()).to(directExchange).with(getRoutingKey());
            case FanoutExchange fanoutExchange -> BindingBuilder.bind(queue()).to(fanoutExchange);
            case null, default -> throw new IllegalArgumentException("Unsupported exchange type for binding");
        };
    }


    @Bean
    public AsyncRabbitTemplate templateAsync(ConnectionFactory connectionFactory) {
        return new AsyncRabbitTemplate(new RabbitTemplate(connectionFactory));
    }

    @Bean
    public RabbitTemplate template(ConnectionFactory connectionFactory) {
        return new RabbitTemplate(connectionFactory);
    }

    @Bean
    public RabbitmqPublisher rabbitmqPublisher(RabbitProperties rabbitProperties, AsyncRabbitTemplate asyncRabbitTemplate, RabbitTemplate rabbitTemplate) {
        return new RabbitmqPublisherImpl(rabbitProperties, asyncRabbitTemplate, rabbitTemplate);
    }

    @Bean
    @ConditionalOnMissingClass
    public RabbitmqReceiver rabbitmqReceiver(PersistMessageProcessor persistMessageProcessor) {
        return new RabbitmqReceiverImpl(this, persistMessageProcessor);
    }
}