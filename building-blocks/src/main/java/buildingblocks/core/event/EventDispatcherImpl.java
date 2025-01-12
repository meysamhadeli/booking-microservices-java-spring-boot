package buildingblocks.core.event;

import buildingblocks.outboxprocessor.PersistMessageProcessor;
import buildingblocks.outboxprocessor.PersistMessageProcessorConfiguration;
import org.springframework.context.annotation.Import;
import org.springframework.stereotype.Component;
import java.util.List;

@Component
@Import(PersistMessageProcessorConfiguration.class)
public class EventDispatcherImpl implements EventDispatcher {
    private final EventMapper eventMapper;
    private final PersistMessageProcessor persistMessageProcessor;

    public EventDispatcherImpl(EventMapper eventMapper, PersistMessageProcessor persistMessageProcessor) {
        this.eventMapper = eventMapper;
        this.persistMessageProcessor = persistMessageProcessor;
    }

    @Override
    public <T extends DomainEvent> void send(List<T> domainEvents, Class<?> eventType) {
        List<IntegrationEvent> integrationEvents = domainEvents.stream().map(eventMapper::MapToIntegrationEvent).toList();

        integrationEvents.forEach(persistMessageProcessor::publishMessage);

        if (InternalCommand.class.isAssignableFrom(eventType)) {
            List<InternalCommand> internalCommands = domainEvents.stream().map(eventMapper::MapToInternalCommand).toList();
            internalCommands.forEach(persistMessageProcessor::addInternalMessage);
        }
    }
}
