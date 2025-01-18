package buildingblocks.outboxprocessor;

import buildingblocks.core.event.IntegrationEvent;
import buildingblocks.core.event.InternalCommand;
import org.springframework.amqp.core.Message;

import java.util.UUID;

public interface PersistMessageProcessor {
    <T extends IntegrationEvent> void publishMessage(T message);
    <T extends InternalCommand> void addInternalMessage(T message);
    <T extends Message> UUID addReceivedMessage(T message);
    PersistMessageEntity existInboxMessage(UUID messageId);
    void process(UUID messageId, MessageDeliveryType deliveryType);
    void processAll();
    boolean messageIsPublished(Class<?> messageType);
    boolean messageIsDelivered(Class<?> messageType);
}