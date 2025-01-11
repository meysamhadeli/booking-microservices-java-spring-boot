package buildingblocks.rabbitmq;

import buildingblocks.outboxprocessor.PersistMessageEntity;
import buildingblocks.outboxprocessor.PersistMessageProcessor;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.listener.MessageListenerContainer;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import java.util.List;
import java.util.UUID;


public interface RabbitmqReceiver{
    MessageListenerContainer addListeners(List<MessageListener> handlers);
}

class RabbitmqReceiverImpl implements RabbitmqReceiver {

    private final RabbitmqConfiguration rabbitmqConfiguration;
    private final PersistMessageProcessor persistMessageProcessor;

    public RabbitmqReceiverImpl(RabbitmqConfiguration rabbitmqConfiguration, PersistMessageProcessor persistMessageProcessor) {
        this.rabbitmqConfiguration = rabbitmqConfiguration;
        this.persistMessageProcessor = persistMessageProcessor;
    }

    @Override
    public MessageListenerContainer addListeners(List<MessageListener> handlers) {

        SimpleMessageListenerContainer container = new SimpleMessageListenerContainer();
        container.setConnectionFactory(this.rabbitmqConfiguration.connectionFactory(rabbitmqConfiguration.rabbitmqOptions()));
        container.setQueueNames(this.rabbitmqConfiguration.getQueueName(rabbitmqConfiguration.rabbitmqOptions()));

        if (!handlers.isEmpty()) {
            handlers.forEach(handler -> {
                // Set message listener that routes to appropriate handler
                container.setMessageListener(message -> {
                    if (handler != null) {
                        UUID id = persistMessageProcessor.addReceivedMessage(message);
                        PersistMessageEntity persistMessage = persistMessageProcessor.existInboxMessage(id);

                        if (persistMessage == null)
                        {
                            handler.onMessage(message);
                            persistMessageProcessor.processInbox(id);
                        }
                    }
                });
            });
        }
        return container;
    }
}
