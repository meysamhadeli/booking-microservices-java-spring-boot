package buildingblocks.rabbitmq;


import buildingblocks.core.event.IntegrationEvent;
import buildingblocks.utils.jsonconverter.JsonConverterUtils;
import com.github.f4b6a3.uuid.UuidCreator;
import org.springframework.amqp.core.MessageDeliveryMode;
import org.springframework.amqp.core.MessagePostProcessor;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.AsyncRabbitTemplate;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.scheduling.annotation.Async;

import java.util.Date;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

public interface RabbitmqPublisher{
    @Async
    <T extends IntegrationEvent> void publish(T message);
    @Async
    <T extends IntegrationEvent> CompletableFuture<Void> publishAsync(T message);
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
    public <T extends IntegrationEvent> CompletableFuture<Void> publishAsync(T message) {
        return this.asyncRabbitTemplate.convertSendAndReceive(
                rabbitmqOptions.getExchangeName(),
                this.rabbitmqOptions.getExchangeName() + "_routing_key",
                JsonConverterUtils.serializeObject(message),
                getMessagePostProcessor(message));
    }

    public <T extends IntegrationEvent> void publish(T message) {
         this.rabbitTemplate.convertSendAndReceive(
                rabbitmqOptions.getExchangeName(),
                this.rabbitmqOptions.getExchangeName() + "_routing_key",
                 JsonConverterUtils.serializeObject(message),
                 getMessagePostProcessor(message));
    }

    private <T extends IntegrationEvent> MessagePostProcessor getMessagePostProcessor(T message) {
        return messageProperties -> {
            MessageProperties props = messageProperties.getMessageProperties();
            props.setMessageId(message.toString());
            props.setType(message.getEventType());
            props.setCorrelationId(UuidCreator.getTimeOrderedEpoch().toString());
            props.setHeader("occurredOn", message.getOccurredOn());
            props.setContentType(MessageProperties.CONTENT_TYPE_JSON);
            props.setDeliveryMode(MessageDeliveryMode.PERSISTENT);
            return messageProperties;
        };
    }
}
