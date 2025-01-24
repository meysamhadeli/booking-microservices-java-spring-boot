package buildingblocks.mediator.abstractions.notifications;

public interface INotificationHandler<TNotification extends INotification> {

    Void handle(TNotification notification);
}
