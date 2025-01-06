package buildingblocks.jpa;

import buildingblocks.core.event.DomainEvent;
import buildingblocks.core.event.EventMapper;
import buildingblocks.core.event.IntegrationEvent;
import buildingblocks.core.model.AggregateRoot;
import buildingblocks.rabbitmq.RabbitmqPublisher;
import org.axonframework.modelling.command.AggregateLifecycle;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.support.TransactionSynchronizationAdapter;
import org.springframework.transaction.support.TransactionSynchronizationManager;
import org.springframework.transaction.support.TransactionTemplate;

import java.util.List;
import java.util.function.Supplier;

@Configuration
public class TransactionCoordinator {

  private final TransactionTemplate transactionTemplate;
  private final RabbitmqPublisher rabbitmqPublisher;
  private final EventMapper eventMapper;

  public TransactionCoordinator(
    PlatformTransactionManager transactionManager,
    RabbitmqPublisher rabbitmqPublisher,
    EventMapper eventMapper) {
    this.transactionTemplate = new TransactionTemplate(transactionManager);
    this.rabbitmqPublisher = rabbitmqPublisher;
    this.eventMapper = eventMapper;
  }

  public <T extends AggregateRoot<?>> T executeWithEvents(Supplier<T> action) {
    return transactionTemplate.execute(status -> {
      try {
        // Execute the transactional logic
        T aggregate = action.get();

        // After successful transaction, publish events
        List<DomainEvent> events = aggregate.getDomainEvents();
        List<IntegrationEvent> integrationEvents = events.stream().map(eventMapper::MapToIntegrationEvent).toList();
        publishEventsAfterCommit(integrationEvents);

        aggregate.clearDomainEvents();

        return aggregate;
      } catch (Exception ex) {
        status.setRollbackOnly(); // Mark transaction for rollback
        throw ex;
      }
    });
  }

  private void publishEventsAfterCommit(List<IntegrationEvent> events) {
    TransactionSynchronizationManager.registerSynchronization(new TransactionSynchronizationAdapter() {
      @Override
      public void afterCommit() {
        // Publish each event to the broker and axon event handler
        events.forEach(event -> {
          rabbitmqPublisher.publish(event);
          AggregateLifecycle.apply(event);
        });
      }
    });
  }
}
