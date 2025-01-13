package buildingblocks.mediator.abstractions;

import buildingblocks.mediator.abstractions.notifications.INotification;

public interface IPublisher {
    <TNotification extends INotification> Void publish(TNotification notification) throws Exception;
}
