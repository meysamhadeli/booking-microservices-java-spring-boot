package buildingblocks.rabbitmq;

import buildingblocks.utils.jsonconverter.JsonConverterUtils;
import buildingblocks.utils.reflection.ReflectionUtils;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageListener;

public interface MessageHandler<T> extends MessageListener {
  /**
   * Handles the message of the specified type.
   */
  void onMessage(T message);

  /**
   * Default implementation of onMessage to delegate to the consume method.
   */
  @Override
  default void onMessage(Message message) {
    try {
      Class<T> messageType = ReflectionUtils.getGenericTypeParameter(this.getClass(), MessageHandler.class);
      T deserializedMessage = JsonConverterUtils.deserialize(message.getBody(), messageType);
      onMessage(deserializedMessage);
    } catch (Exception ex) {
      throw new RuntimeException("Failed to process message", ex);
    }
  }
}
