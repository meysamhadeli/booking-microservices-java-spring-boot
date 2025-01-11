package buildingblocks.outboxprocessor;

import buildingblocks.core.event.IntegrationEvent;
import buildingblocks.core.event.InternalCommand;
import org.springframework.amqp.core.Message;

import java.util.UUID;

public interface PersistMessageProcessor {
    public <T extends IntegrationEvent> void publishMessage(T message);
    public <T extends InternalCommand> void addInternalMessage(T message);
    public <T extends Message> UUID addReceivedMessage(T message);
    public PersistMessageEntity existInboxMessage(UUID messageId);
    public void process(UUID messageId, MessageDeliveryType deliveryType);
    public void processAll();
    public void processInbox(UUID messageId);
}