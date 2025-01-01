package buildingblocks.rabbitmq;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.listener.MessageListenerContainer;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import java.util.List;


public interface RabbitmqReceiver{
    MessageListenerContainer addListeners(List<MessageListener> handlers);
}

@Configuration
@Import({RabbitmqConfiguration.class})
class RabbitmqReceiverImpl implements RabbitmqReceiver {

    private final RabbitmqOptions rabbitmqOptions;
    private final ConnectionFactory connectionFactory;

    public RabbitmqReceiverImpl(RabbitmqOptions rabbitmqOptions, ConnectionFactory connectionFactory) {
        this.rabbitmqOptions = rabbitmqOptions;
        this.connectionFactory = connectionFactory;
    }

    public String getQueueName() {
        return rabbitmqOptions.getExchangeName() + "_queue";
    }

    public String getRoutingKey() {
        return rabbitmqOptions.getExchangeName() + "_routing_key";
    }

    @Bean
    public Queue queue() {
        return new Queue(getQueueName(), true);
    }

    @Bean
    public Exchange exchange() {
        return switch (rabbitmqOptions.getExchangeType().toLowerCase()) {
            case "direct" -> new DirectExchange(rabbitmqOptions.getExchangeName());
            case "fanout" -> new FanoutExchange(rabbitmqOptions.getExchangeName());
            default -> new TopicExchange(rabbitmqOptions.getExchangeName());
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

    @Override
    public MessageListenerContainer addListeners(List<MessageListener> handlers) {

        SimpleMessageListenerContainer container = new SimpleMessageListenerContainer();
        container.setConnectionFactory(this.connectionFactory);
        container.setQueueNames(this.getQueueName());

        if (!handlers.isEmpty()) {
            handlers.forEach(handler -> {
                // Set message listener that routes to appropriate handler
                container.setMessageListener(message -> {
                    if (handler != null) {
                        handler.onMessage(message);
                    }
                });
            });
        }
        return container;
    }
}
