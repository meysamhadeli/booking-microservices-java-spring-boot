package buildingblocks.outboxprocessor;

import buildingblocks.mediator.MediatorConfiguration;
import buildingblocks.mediator.abstractions.IMediator;
import buildingblocks.rabbitmq.RabbitmqPublisher;
import jakarta.persistence.EntityManager;
import org.slf4j.Logger;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingClass;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
@Import(MediatorConfiguration.class)
public class PersistMessageProcessorConfiguration {

    @Bean
    @ConditionalOnMissingClass
    public PersistMessageProcessor persistMessageProcessor(
            EntityManager entityManager,
            RabbitmqPublisher rabbitmqPublisher,
            Logger logger,
            IMediator mediator,
            ApplicationContext applicationContext) {
        return new PersistMessageProcessorImpl(entityManager, rabbitmqPublisher, logger, mediator, applicationContext);
    }

    @Bean
    @ConditionalOnMissingClass
    public PersistMessageBackgroundJob persistMessageBackgroundJob(
            TaskScheduler taskScheduler,
            PersistMessageProcessor persistMessageProcessor,
            Logger logger,
            PlatformTransactionManager transactionManager) {
        return new PersistMessageBackgroundJob(taskScheduler, persistMessageProcessor, logger, transactionManager);
    }
}

