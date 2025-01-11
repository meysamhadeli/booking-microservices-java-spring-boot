package buildingblocks.outboxprocessor;

import buildingblocks.core.event.IntegrationEvent;
import buildingblocks.core.event.InternalCommand;
import buildingblocks.jpa.JpaConfiguration;
import buildingblocks.logger.LoggerConfiguration;
import buildingblocks.rabbitmq.RabbitmqConfiguration;
import buildingblocks.rabbitmq.RabbitmqPublisher;
import buildingblocks.utils.jsonconverter.JsonConverter;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import org.slf4j.Logger;
import org.springframework.amqp.core.Message;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import java.util.List;
import java.util.UUID;

@Configuration
@Import({PersistMessageProcessorConfiguration.class, RabbitmqConfiguration.class, JpaConfiguration.class, LoggerConfiguration.class})
public class PersistMessageProcessorImpl implements PersistMessageProcessor {
    private final RabbitmqPublisher rabbitmqPublisher;
    private final EntityManager entityManager;
    private final JPAQueryFactory queryFactory;
    private final Logger logger;

    // Generated Q class from QueryDSL
    private final QPersistMessageEntity qPersistMessageEntity = QPersistMessageEntity.persistMessageEntity;

    public PersistMessageProcessorImpl(
            EntityManager entityManager,
            RabbitmqPublisher rabbitmqPublisher,
            Logger logger) {
        this.queryFactory = new JPAQueryFactory(entityManager);
        this.rabbitmqPublisher = rabbitmqPublisher;
        this.entityManager = entityManager;
        this.logger = logger;
    }

    public <T extends IntegrationEvent> void publishMessage(T message) {
        savePersistMessage(message, MessageDeliveryType.Outbox, message.getClass().getTypeName());
    }

    public <T extends InternalCommand> void addInternalMessage(T message) {
        savePersistMessage(message, MessageDeliveryType.Internal, message.getClass().getTypeName());
    }

    public  <T extends Message> UUID addReceivedMessage(T message) {
        return savePersistMessage(message, MessageDeliveryType.Inbox, message.getClass().getTypeName()).getId();
    }

    public PersistMessageEntity existInboxMessage(UUID messageId) {
        return queryFactory
                .selectFrom(qPersistMessageEntity)
                .where(qPersistMessageEntity.id.eq(messageId)
                        .and(qPersistMessageEntity.deliveryType.eq(MessageDeliveryType.Inbox))
                        .and(qPersistMessageEntity.messageStatus.eq(MessageStatus.Processed)))
                .fetchOne();
    }

    public void process(UUID messageId, MessageDeliveryType deliveryType) {
        PersistMessageEntity message = queryFactory
                .selectFrom(qPersistMessageEntity)
                .where(qPersistMessageEntity.id.eq(messageId)
                        .and(qPersistMessageEntity.deliveryType.eq(deliveryType)))
                .fetchOne();

        if (message == null) {
            return;
        }

        boolean processed = switch (deliveryType) {
            case Internal -> processInternal(message);
            case Outbox -> processOutbox(message);
            default -> false;
        };

        if (processed) {
            changeMessageStatus(message, MessageStatus.Processed);
        }
    }

    public void processAll() {
        List<PersistMessageEntity> messages = queryFactory
                .selectFrom(qPersistMessageEntity)
                .where(qPersistMessageEntity.messageStatus.ne(MessageStatus.Processed))
                .fetch();

        for (PersistMessageEntity message : messages) {
            process(message.getId(), message.getDeliveryType());
        }
    }

    public void processInbox(UUID messageId) {
        PersistMessageEntity message = queryFactory
                .selectFrom(qPersistMessageEntity)
                .where(qPersistMessageEntity.id.eq(messageId)
                        .and(qPersistMessageEntity.deliveryType.eq(MessageDeliveryType.Inbox))
                        .and(qPersistMessageEntity.messageStatus.eq(MessageStatus.InProgress)))
                .fetchOne();

        if (message != null) {
            changeMessageStatus(message, MessageStatus.Processed);
        }
    }

    private boolean processOutbox(PersistMessageEntity message) {
        try {
            Class<?> dataType = Class.forName(message.getDataType());

            Object data = JsonConverter.deserialize(message.getData(), dataType);

            rabbitmqPublisher.publish(data);

            logger.info("Message with id: {} and delivery type: {} processed from the persistence message store.",
                    message.getId(), message.getDeliveryType().toString());

            return true;
        } catch (Exception ex) {
            throw new RuntimeException("Failed to process outbox message: " + message.getId(), ex);
        }
    }

    private boolean processInternal(PersistMessageEntity message) {
        try {
            Class<?> type = Class.forName(message.getDataType());
            String serializedMessage = JsonConverter.serializeObject(message);

            Object data = JsonConverter.deserialize(serializedMessage, type);

            logger.info("InternalCommand with id: {} and delivery type: {} processed from the persistence message store.",
                    message.getId(), message.getDeliveryType().toString());

            return true;
        } catch (Exception ex) {
            throw new RuntimeException("Failed to process internal message: " + message.getId(), ex);
        }
    }

    private <T> PersistMessageEntity savePersistMessage(
            T message,
            MessageDeliveryType deliveryType,
            String dataType) {

        PersistMessageEntity persistMessageEntity = PersistMessageEntity.create(
                dataType,
                JsonConverter.serializeObject(message),
                deliveryType
        );

        entityManager.persist(persistMessageEntity);
        entityManager.flush();

        logger.info("Message with id: {} and delivery type: {} saved in persistence message store.",
                persistMessageEntity.getId(), deliveryType.toString());

        return persistMessageEntity;
    }

    private void changeMessageStatus(PersistMessageEntity message, MessageStatus status) {
        queryFactory
                .update(qPersistMessageEntity)
                .set(qPersistMessageEntity.messageStatus, status)
                .where(qPersistMessageEntity.id.eq(message.getId()))
                .execute();

        entityManager.flush();
    }
}
