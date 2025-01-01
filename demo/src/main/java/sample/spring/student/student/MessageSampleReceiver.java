package sample.spring.student.student;

import buildingblocks.utils.jsonconverter.JsonConverter;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageListener;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.stereotype.Component;

@Component
public class MessageSampleReceiver implements MessageListener {

    @Override
    public void onMessage(Message message) {
        try {
            // Get message properties
            MessageProperties props = message.getMessageProperties();
            String correlationId = props.getCorrelationId();
            String messageId = props.getMessageId();


            // Convert message to User object
            var studentCreated = JsonConverter.deserialize(message.getBody(), StudentCreated.class);

            System.out.println("Received user message:");
            System.out.println(studentCreated.getName());
            System.out.println(studentCreated.getId());


        } catch (Exception e) {
            System.out.println("Error processing user message");
        }
    }
}