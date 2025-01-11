package buildingblocks.rabbitmq;

import buildingblocks.utils.jsonconverter.JsonConverter;
import org.springframework.amqp.core.MessageDeliveryMode;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.AsyncRabbitTemplate;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.scheduling.annotation.Async;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

public interface RabbitmqPublisher {
    @Async
    <T> CompletableFuture<Void> publishAsync(T message);
    <T> void publish(T message);
}

class RabbitmqPublisherImpl implements RabbitmqPublisher {
    private final RabbitmqOptions rabbitmqOptions;
    private final AsyncRabbitTemplate asyncRabbitTemplate;
    private final RabbitTemplate rabbitTemplate;

    public RabbitmqPublisherImpl(RabbitmqOptions rabbitmqOptions, AsyncRabbitTemplate asyncRabbitTemplate, RabbitTemplate rabbitTemplate) {
        this.rabbitmqOptions = rabbitmqOptions;
        this.asyncRabbitTemplate = asyncRabbitTemplate;
        this.rabbitTemplate = rabbitTemplate;
    }

    @Override
    @Async
    public <T> CompletableFuture<Void> publishAsync(T message) {
        return this.asyncRabbitTemplate.convertSendAndReceive(
                rabbitmqOptions.getExchangeName(),
                rabbitmqOptions.getExchangeName() + "_routing_key",
                JsonConverter.serializeObject(message),
                messageProperties -> {
                    MessageProperties props = messageProperties.getMessageProperties();
                    props.setContentType(MessageProperties.CONTENT_TYPE_JSON);
                    props.setCorrelationId(UUID.randomUUID().toString());
                    props.setMessageId(UUID.randomUUID().toString());
                    props.setDeliveryMode(MessageDeliveryMode.PERSISTENT);
                    return messageProperties;
                });
    }

    @Override
    public <T> void publish(T message) {
        this.rabbitTemplate.convertAndSend(
                rabbitmqOptions.getExchangeName(),
                rabbitmqOptions.getExchangeName() + "_routing_key",
                JsonConverter.serializeObject(message),
                messageProperties -> {
                    MessageProperties props = messageProperties.getMessageProperties();
                    props.setContentType(MessageProperties.CONTENT_TYPE_JSON);
                    props.setCorrelationId(UUID.randomUUID().toString());
                    props.setMessageId(UUID.randomUUID().toString());
                    props.setDeliveryMode(MessageDeliveryMode.PERSISTENT);
                    return messageProperties;
                });
    }
}
