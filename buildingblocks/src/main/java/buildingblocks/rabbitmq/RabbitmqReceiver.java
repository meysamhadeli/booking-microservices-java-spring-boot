package buildingblocks.rabbitmq;

import buildingblocks.core.event.EventDispatcher;
import buildingblocks.jpa.JpaConfiguration;
import buildingblocks.outboxprocessor.MessageDeliveryType;
import buildingblocks.outboxprocessor.PersistMessageEntity;
import buildingblocks.outboxprocessor.PersistMessageProcessor;
import buildingblocks.outboxprocessor.PersistMessageProcessorConfiguration;
import buildingblocks.utils.reflection.ReflectionUtils;
import org.slf4j.Logger;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.listener.MessageListenerContainer;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.support.TransactionTemplate;

import java.util.List;
import java.util.UUID;


public interface RabbitmqReceiver {
    MessageListenerContainer addListeners();
    boolean messageIsReceived(UUID messageId);
}

@Configuration
@Import({RabbitmqConfiguration.class, PersistMessageProcessorConfiguration.class, JpaConfiguration.class})
class RabbitmqReceiverImpl implements RabbitmqReceiver {

    private final RabbitmqConfiguration rabbitmqConfiguration;
    private final PersistMessageProcessor persistMessageProcessor;
    private final TransactionTemplate transactionTemplate;

    public RabbitmqReceiverImpl(RabbitmqConfiguration rabbitmqConfiguration, PersistMessageProcessor persistMessageProcessor, PlatformTransactionManager platformTransactionManager) {
        this.rabbitmqConfiguration = rabbitmqConfiguration;
        this.persistMessageProcessor = persistMessageProcessor;
        this.transactionTemplate = new TransactionTemplate(platformTransactionManager);
    }

    @Override
    public MessageListenerContainer addListeners() {

        SimpleMessageListenerContainer container = new SimpleMessageListenerContainer();
        container.setConnectionFactory(rabbitmqConfiguration.connectionFactory());
        container.setQueueNames(rabbitmqConfiguration.getQueueName());

        // Find all MessageListener implementations
        var handler = ReflectionUtils.getInstanceOfSubclass(MessageListener.class);
        if (handler != null) {
            // Set message listener that routes to appropriate handler
            container.setMessageListener(message -> {
                transactionTemplate.execute(status -> {
                    try {
                        // Add the received message to the inbox
                        UUID id = persistMessageProcessor.addReceivedMessage(message);
                        PersistMessageEntity persistMessage = persistMessageProcessor.existInboxMessage(id);

                        if (persistMessage == null) {
                            handler.onMessage(message);
                            persistMessageProcessor.process(id, MessageDeliveryType.Inbox);
                        }
                    } catch (Exception ex) {
                        status.setRollbackOnly();
                        throw ex;
                    }
                    return null; // TransactionTemplate requires a return value
                });
            });
        }

        return container;
    }

    @Override
    public boolean messageIsReceived(UUID messageId) {
        return false;
    }
}
