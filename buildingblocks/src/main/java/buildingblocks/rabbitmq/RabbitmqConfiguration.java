package buildingblocks.rabbitmq;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.AsyncRabbitTemplate;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitmqConfiguration {

    @Bean
    public RabbitmqOptions rabbitmqOptions() {
        return new RabbitmqOptions();
    }

    @Bean
    public ConnectionFactory connectionFactory(RabbitmqOptions rabbitmqOptions) {
        CachingConnectionFactory connectionFactory = new CachingConnectionFactory(rabbitmqOptions.getHost());
        connectionFactory.setPort(rabbitmqOptions.getPort());
        connectionFactory.setUsername(rabbitmqOptions.getUsername());
        connectionFactory.setPassword(rabbitmqOptions.getPassword());
        return connectionFactory;
    }

    public String getQueueName(RabbitmqOptions rabbitmqOptions) {
        return rabbitmqOptions.getExchangeName() + "_queue";
    }

    public String getRoutingKey(RabbitmqOptions rabbitmqOptions) {
        return rabbitmqOptions.getExchangeName() + "_routing_key";
    }

    @Bean
    public Queue queue(RabbitmqOptions rabbitmqOptions) {
        return new Queue(rabbitmqOptions.getExchangeName() + "_queue", true);
    }

    @Bean
    public Exchange exchange(RabbitmqOptions rabbitmqOptions) {
        return switch (rabbitmqOptions.getExchangeType().toLowerCase()) {
            case "direct" -> new DirectExchange(rabbitmqOptions.getExchangeName());
            case "fanout" -> new FanoutExchange(rabbitmqOptions.getExchangeName());
            default -> new TopicExchange(rabbitmqOptions.getExchangeName());
        };
    }


    @Bean
    public Binding binding(Queue queue, Exchange exchange, RabbitmqOptions rabbitmqOptions) {
        String routingKey = rabbitmqOptions.getExchangeName() + "_routing_key";
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
    public RabbitmqPublisher rabbitmqPublisher(RabbitmqOptions rabbitmqOptions, AsyncRabbitTemplate asyncRabbitTemplate, RabbitTemplate rabbitTemplate) {
        return new RabbitmqPublisherImpl(rabbitmqOptions, asyncRabbitTemplate, rabbitTemplate);
    }

    @Bean
    public RabbitmqReceiver rabbitmqReceiver() {
        return new RabbitmqReceiverImpl(this);
    }
}