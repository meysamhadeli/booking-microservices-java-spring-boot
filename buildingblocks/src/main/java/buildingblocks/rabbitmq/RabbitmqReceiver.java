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

    private final RabbitmqConfiguration rabbitmqConfiguration;

    public RabbitmqReceiverImpl(RabbitmqConfiguration rabbitmqConfiguration) {
        this.rabbitmqConfiguration = rabbitmqConfiguration;
    }

    @Override
    public MessageListenerContainer addListeners(List<MessageListener> handlers) {

        SimpleMessageListenerContainer container = new SimpleMessageListenerContainer();
        container.setConnectionFactory(rabbitmqConfiguration.connectionFactory(rabbitmqConfiguration.rabbitmqOptions()));
        container.setQueueNames(rabbitmqConfiguration.getQueueName(rabbitmqConfiguration.rabbitmqOptions()));

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
