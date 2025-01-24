package buildingblocks.outboxprocessor;

import buildingblocks.mediator.MediatorConfiguration;
import buildingblocks.mediator.abstractions.IMediator;
import jakarta.persistence.EntityManager;
import org.slf4j.Logger;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.boot.autoconfigure.amqp.RabbitProperties;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingClass;
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
            RabbitTemplate rabbitTemplate,
            RabbitProperties rabbitProperties,
            Logger logger,
            IMediator mediator) {
        return new PersistMessageProcessorImpl(entityManager, rabbitTemplate, rabbitProperties, logger, mediator);
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

