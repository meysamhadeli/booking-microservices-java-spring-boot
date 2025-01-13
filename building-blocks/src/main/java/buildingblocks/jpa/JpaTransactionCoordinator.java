package buildingblocks.jpa;

import buildingblocks.core.event.*;
import buildingblocks.core.model.AggregateRoot;
import org.slf4j.Logger;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.support.TransactionTemplate;
import java.util.List;
import java.util.function.Supplier;

@Configuration
public class JpaTransactionCoordinator {

    private final TransactionTemplate transactionTemplate;
    private final Logger logger;
    private final EventDispatcher eventDispatcher;

    public JpaTransactionCoordinator(
            PlatformTransactionManager platformTransactionManager,
            Logger logger,
            EventDispatcher eventDispatcher) {
        this.transactionTemplate = new TransactionTemplate(platformTransactionManager);
        this.logger = logger;
        this.eventDispatcher = eventDispatcher;
    }

    public <T extends AggregateRoot<?>> T executeWithEvents(Supplier<T> action, Class<?> type) {
        return transactionTemplate.execute(status -> {
            try {
                // Execute the transactional logic
                T aggregate = action.get();

                // Validate the result is an AggregateRoot
                if (aggregate instanceof AggregateRoot<?> aggregateRoot) {
                    List<DomainEvent> domainEvents = aggregateRoot.getDomainEvents();

                    this.eventDispatcher.send(domainEvents, type);

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
