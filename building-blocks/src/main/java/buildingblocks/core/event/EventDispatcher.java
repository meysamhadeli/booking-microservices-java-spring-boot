package buildingblocks.core.event;

import java.util.List;

public interface EventDispatcher {
   <T extends DomainEvent> void send(List<T> domainEvents, Class<?> eventType);
}
