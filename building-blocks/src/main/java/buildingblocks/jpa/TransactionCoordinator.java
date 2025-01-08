package buildingblocks.jpa;

import buildingblocks.core.event.DomainEvent;
import buildingblocks.core.event.EventMapper;
import buildingblocks.core.event.IntegrationEvent;
import buildingblocks.core.model.AggregateRoot;
import buildingblocks.rabbitmq.RabbitmqPublisher;
import org.slf4j.Logger;
import org.springframework.stereotype.Component;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.support.TransactionSynchronization;
import org.springframework.transaction.support.TransactionSynchronizationManager;
import org.springframework.transaction.support.TransactionTemplate;
import java.util.List;
import java.util.function.Supplier;

@Component
public class TransactionCoordinator {

    private final TransactionTemplate transactionTemplate;
    private final RabbitmqPublisher rabbitmqPublisher;
    private final EventMapper eventMapper;
    private final Logger logger;

    public TransactionCoordinator(
            PlatformTransactionManager platformTransactionManager,
            RabbitmqPublisher rabbitmqPublisher,
            Logger logger,
            EventMapper eventMapper) {
        this.transactionTemplate = new TransactionTemplate(platformTransactionManager);
        this.rabbitmqPublisher = rabbitmqPublisher;
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
                    handleAggregateRoot(aggregateRoot);
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

    private void handleAggregateRoot(AggregateRoot<?> aggregateRoot) {
        // After successful transaction, publish events
        List<DomainEvent> events = aggregateRoot.getDomainEvents();
        List<IntegrationEvent> integrationEvents = events.stream().map(eventMapper::MapToIntegrationEvent).toList();
        publishEventsAfterCommit(integrationEvents);

        aggregateRoot.clearDomainEvents();
    }

    private void publishEventsAfterCommit(List<IntegrationEvent> events) {
        TransactionSynchronizationManager.registerSynchronization(new TransactionSynchronization() {
            @Override
            public void afterCommit() {
                // Publish each event to the broker
                events.forEach(rabbitmqPublisher::publish);
            }
        });
    }
}
