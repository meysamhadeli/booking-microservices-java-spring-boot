package buildingblocks.jpa;

import buildingblocks.core.event.DomainEvent;
import buildingblocks.core.event.EventMapper;
import buildingblocks.core.event.IntegrationEvent;
import buildingblocks.core.model.AggregateRoot;
import buildingblocks.outboxprocessor.PersistMessageProcessor;
import org.slf4j.Logger;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.support.TransactionTemplate;
import java.util.List;
import java.util.function.Supplier;

@Configuration
public class JpaTransactionCoordinator {

    private final TransactionTemplate transactionTemplate;
    private final PersistMessageProcessor persistMessageProcessor;
    private final EventMapper eventMapper;
    private final Logger logger;

    public JpaTransactionCoordinator(
            PlatformTransactionManager platformTransactionManager,
            PersistMessageProcessor persistMessageProcessor,
            Logger logger,
            EventMapper eventMapper) {
        this.transactionTemplate = new TransactionTemplate(platformTransactionManager);
        this.persistMessageProcessor = persistMessageProcessor;
        this.eventMapper = eventMapper;
        this.logger = logger;
    }

    public <T extends AggregateRoot<?>> T executeWithEvents(Supplier<T> action) {
        return transactionTemplate.execute(status -> {
            try {
                // Execute the transactional logic
                T aggregate = action.get();

                // Validate the result is an AggregateRoot
                if (aggregate instanceof AggregateRoot<?> aggregateRoot) {
                    List<DomainEvent> events = aggregateRoot.getDomainEvents();
                    List<IntegrationEvent> integrationEvents = events.stream().map(eventMapper::MapToIntegrationEvent).toList();

                    integrationEvents.forEach(persistMessageProcessor::publishMessage);

                    aggregateRoot.clearDomainEvents();

                    logger.info("Transaction successfully committed.");
                    return aggregate;
                }

                return aggregate;
            } catch (Exception ex) {
                status.setRollbackOnly();

                logger.error("Transaction is rolled back.");
                throw ex;
            }
        });
    }
}
