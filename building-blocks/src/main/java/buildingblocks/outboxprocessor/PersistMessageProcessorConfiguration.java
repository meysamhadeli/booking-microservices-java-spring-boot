package buildingblocks.outboxprocessor;

import buildingblocks.rabbitmq.RabbitmqPublisher;
import jakarta.persistence.EntityManager;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.slf4j.Logger;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingClass;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
public class PersistMessageProcessorConfiguration {

    @Bean
    @ConditionalOnMissingClass
    public PersistMessageProcessor persistMessageProcessor(
            EntityManager entityManager,
            RabbitmqPublisher rabbitmqPublisher,
            Logger logger,
            CommandGateway commandGateway) {
        return new PersistMessageProcessorImpl(entityManager, rabbitmqPublisher, logger, commandGateway);
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

