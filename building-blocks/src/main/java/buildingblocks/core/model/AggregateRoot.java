package buildingblocks.core.model;

import buildingblocks.core.event.DomainEvent;
import lombok.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public abstract class AggregateRoot<T> {
    private T id;

    private static final  List<DomainEvent> domainEvents = new ArrayList<>();

    public List<DomainEvent> getDomainEvents() {
        return Collections.unmodifiableList(domainEvents);
    }

    public void addDomainEvent(DomainEvent domainEvent) {
        domainEvents.add(domainEvent);
    }

    public void clearDomainEvents() {
        domainEvents.clear();
    }
}
