package buildingblocks.outboxprocessor;

import buildingblocks.core.event.IntegrationEvent;
import buildingblocks.core.event.InternalCommand;
import buildingblocks.jpa.JpaConfiguration;
import buildingblocks.logger.LoggerConfiguration;
import buildingblocks.mediator.abstractions.IMediator;
import buildingblocks.mediator.abstractions.commands.ICommand;
import buildingblocks.rabbitmq.RabbitmqConfiguration;
import buildingblocks.utils.jsonconverter.JsonConverterUtils;
import buildingblocks.utils.reflection.ReflectionUtils;
import com.github.f4b6a3.uuid.UuidCreator;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import org.slf4j.Logger;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageDeliveryMode;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.boot.autoconfigure.amqp.RabbitProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import java.util.List;
import java.util.UUID;

@Configuration
@Import({PersistMessageProcessorConfiguration.class, RabbitmqConfiguration.class, RabbitProperties.class, JpaConfiguration.class, LoggerConfiguration.class})
public class PersistMessageProcessorImpl implements PersistMessageProcessor {
  private final RabbitTemplate rabbitTemplate;
  private final RabbitProperties rabbitProperties;
  private final EntityManager entityManager;
  private final JPAQueryFactory queryFactory;
  private final Logger logger;
  private final IMediator mediator;

  // Generated Q class from QueryDSL
  private final QPersistMessageEntity qPersistMessageEntity = QPersistMessageEntity.persistMessageEntity;

  public PersistMessageProcessorImpl(
    EntityManager entityManager,
    RabbitTemplate rabbitmqTemplate,
    RabbitProperties rabbitProperties,
    Logger logger,
    IMediator mediator) {
    this.queryFactory = new JPAQueryFactory(entityManager);
    this.rabbitTemplate = rabbitmqTemplate;
    this.rabbitProperties = rabbitProperties;
    this.entityManager = entityManager;
    this.logger = logger;
    this.mediator = mediator;
  }

  public <T extends IntegrationEvent> void publishMessage(T message) {
    savePersistMessage(message, MessageDeliveryType.Outbox, message.getClass().getTypeName());
  }

  public <T extends InternalCommand> void addInternalMessage(T message) {
    savePersistMessage(message, MessageDeliveryType.Internal, message.getClass().getTypeName());
  }

  public <T extends Message> UUID addReceivedMessage(T message) {
    String msgBodyTypeName = message.getMessageProperties().getType();
    Class<?> msgBodyType = ReflectionUtils.findClassFromName(msgBodyTypeName);

    var msg = JsonConverterUtils.deserialize(message.getBody(), msgBodyType);
    return savePersistMessage(msg, MessageDeliveryType.Inbox, msgBodyTypeName).getId();
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
        .and(qPersistMessageEntity.messageStatus.eq(MessageStatus.InProgress))
        .and(qPersistMessageEntity.deliveryType.eq(deliveryType)))
      .fetchOne();

    if (message == null) {
      return;
    }

    boolean processed = switch (deliveryType) {
      case Internal -> processInternal(message);
      case Outbox -> processOutbox(message);
      case Inbox -> processInbox(message);
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

  public boolean messageIsPublished(Class<?> messageType) {
    return queryFactory
      .selectOne()
      .from(qPersistMessageEntity)
      .where(qPersistMessageEntity.dataType.eq(messageType.getTypeName())
        .and(qPersistMessageEntity.deliveryType.eq(MessageDeliveryType.Outbox))
        .and(qPersistMessageEntity.messageStatus.eq(MessageStatus.Processed)))
      .orderBy(qPersistMessageEntity.created.desc())
      .limit(1) // Only fetch the last message
      .fetchOne() != null;
  }

  public boolean messageIsDelivered(Class<?> messageType) {
    return queryFactory
      .selectOne()
      .from(qPersistMessageEntity)
      .where(qPersistMessageEntity.dataType.eq(messageType.getTypeName())
        .and(qPersistMessageEntity.deliveryType.eq(MessageDeliveryType.Inbox))
        .and(qPersistMessageEntity.messageStatus.eq(MessageStatus.Processed)))
      .orderBy(qPersistMessageEntity.created.desc())
      .limit(1) // Only fetch the last message
      .fetchOne() != null;
  }

  private boolean processInbox(PersistMessageEntity message) {
    if (message != null) {
      changeMessageStatus(message, MessageStatus.Processed);
      return true;
    }
    return false;
  }

  private boolean processOutbox(PersistMessageEntity message) {
    try {
      Class<?> dataType = ReflectionUtils.findClassFromName(message.getDataType());

      Object data = JsonConverterUtils.deserialize(message.getData(), dataType);

      if (data instanceof IntegrationEvent integrationEvent) {

        this.rabbitTemplate.convertSendAndReceive(
          rabbitProperties.getTemplate().getExchange(),
          message.getDataType(),
          JsonConverterUtils.serializeObject(integrationEvent), msg -> {
            MessageProperties props = msg.getMessageProperties();
            props.setMessageId(integrationEvent.getEventId().toString());
            props.setType(integrationEvent.getEventType());
            props.setCorrelationId(UuidCreator.getTimeOrderedEpoch().toString());
            props.setHeader("occurredOn", integrationEvent.getOccurredOn());
            props.setContentType(MessageProperties.CONTENT_TYPE_JSON);
            props.setDeliveryMode(MessageDeliveryMode.PERSISTENT);
            return msg;
          });

        logger.info("Message with id: {} and delivery type: {} processed from the persistence message store.",
          message.getId(), message.getDeliveryType().toString());

        return true;
      }
      return false;
    } catch (Exception ex) {
      throw new RuntimeException("Failed to process outbox message: " + message.getId(), ex);
    }
  }

  private boolean processInternal(PersistMessageEntity message) {
    try {
      Class<?> dataType = ReflectionUtils.findClassFromName(message.getDataType());

      Object data = JsonConverterUtils.deserialize(message.getData(), dataType);

      if (data instanceof InternalCommand && data instanceof ICommand<?> command) {

        mediator.send(command);

        logger.info("InternalCommand with id: {} and delivery type: {} processed from the persistence message store.",
          message.getId(), message.getDeliveryType().toString());

        return true;
      }

      return false;

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
      JsonConverterUtils.serializeObject(message),
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
