package buildingblocks.outboxprocessor;

import buildingblocks.rabbitmq.RabbitmqPublisher;
import jakarta.persistence.EntityManager;
import org.slf4j.Logger;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingClass;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.transaction.PlatformTransactionManager;
import javax.sql.DataSource;

@Configuration
public class PersistMessageProcessorConfiguration {

    @Bean
    @ConditionalOnMissingClass
    public PersistMessageProcessor persistMessageProcessor(
            DataSource dataSource,
            EntityManager entityManager,
            RabbitmqPublisher rabbitmqPublisher,
            Logger logger) {
        return new PersistMessageProcessorImpl(dataSource, entityManager, rabbitmqPublisher, logger);
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
