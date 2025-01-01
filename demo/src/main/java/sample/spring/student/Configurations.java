package sample.spring.student;

import buildingblocks.jpa.JpaConfiguration;
import buildingblocks.rabbitmq.*;
import org.springframework.amqp.rabbit.listener.MessageListenerContainer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import sample.spring.student.student.MessageSampleReceiver;
import java.util.List;


@Configuration
@Import({JpaConfiguration.class, RabbitmqConfiguration.class})
public class Configurations {
//    @Bean
//    public FlywayOptions flywayOptions() {
//        return new FlywayOptions();
//    }
//
//    @Bean
//    public FlywayConfiguration flywayConfiguration() {
//        return new FlywayConfiguration(flywayOptions(), dataSource());
//    }

    @Bean
    public MessageListenerContainer addMessageListeners(RabbitmqReceiver rabbitmqReceiver) {
        return rabbitmqReceiver.addListeners(List.of(new MessageSampleReceiver()));
    }
}
