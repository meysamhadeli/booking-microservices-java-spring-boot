package buildingblocks.rabbitmq;

import buildingblocks.core.event.EventDispatcher;
import buildingblocks.jpa.JpaConfiguration;
import buildingblocks.outboxprocessor.PersistMessageEntity;
import buildingblocks.outboxprocessor.PersistMessageProcessor;
import buildingblocks.outboxprocessor.PersistMessageProcessorConfiguration;
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
    MessageListenerContainer addListeners(List<MessageListener> handlers);
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
    public MessageListenerContainer addListeners(List<MessageListener> handlers) {

        SimpleMessageListenerContainer container = new SimpleMessageListenerContainer();
        container.setConnectionFactory(rabbitmqConfiguration.connectionFactory(rabbitmqConfiguration.rabbitmqOptions()));
        container.setQueueNames(rabbitmqConfiguration.getQueueName(rabbitmqConfiguration.rabbitmqOptions()));

        if (!handlers.isEmpty()) {
            handlers.forEach(handler -> {
                // Set message listener that routes to appropriate handler
                container.setMessageListener(message -> {
                    if (handler != null) {
                        transactionTemplate.execute(status -> {
                            try {
                                // Add the received message to the inbox
                                UUID id = persistMessageProcessor.addReceivedMessage(message);
                                PersistMessageEntity persistMessage = persistMessageProcessor.existInboxMessage(id);

                                if (persistMessage == null) {
                                    handler.onMessage(message);
                                    persistMessageProcessor.processInbox(id);
                                }
                            } catch (Exception ex) {
                                status.setRollbackOnly();
                                throw ex;
                            }
                            return null; // TransactionTemplate requires a return value
                        });
                    }
                });
            });
        }
        return container;
    }
}
