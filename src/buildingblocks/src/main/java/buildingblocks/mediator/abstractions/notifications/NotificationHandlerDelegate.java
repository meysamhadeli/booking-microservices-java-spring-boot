package buildingblocks.mediator.abstractions.notifications;

@FunctionalInterface
public interface NotificationHandlerDelegate {
    Void handle();
}
