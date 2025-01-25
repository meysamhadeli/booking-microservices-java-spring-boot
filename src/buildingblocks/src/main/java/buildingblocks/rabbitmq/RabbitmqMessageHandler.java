package buildingblocks.rabbitmq;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface RabbitmqMessageHandler {
  /**
   * Specifies the message type this handler processes.
   * @return the message type class
   */
  Class<?> messageType();

  /**
   * Specifies the queue name for this message handler.
   * @return the queue name
   */
  String queueName();
}
