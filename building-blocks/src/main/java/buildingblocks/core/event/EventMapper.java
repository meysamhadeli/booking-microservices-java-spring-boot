package buildingblocks.core.event;

import org.springframework.stereotype.Component;

@Component
public interface EventMapper {
    IntegrationEvent MapToIntegrationEvent(DomainEvent event);
}
