package buildingblocks.rabbitmq;

import org.springframework.amqp.rabbit.AsyncRabbitTemplate;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitmqConfiguration {

    @Bean
    public RabbitmqOptions rabbitmqOptions() {
        return new RabbitmqOptions();
    }

    @Bean
    @ConditionalOnMissingBean
    public ConnectionFactory connectionFactory(RabbitmqOptions rabbitmqOptions) {
        CachingConnectionFactory connectionFactory = new CachingConnectionFactory(rabbitmqOptions.getHost());
        connectionFactory.setPort(rabbitmqOptions.getPort());
        connectionFactory.setUsername(rabbitmqOptions.getUsername());
        connectionFactory.setPassword(rabbitmqOptions.getPassword());
        return connectionFactory;
    }

    @Bean
    public AsyncRabbitTemplate template(ConnectionFactory connectionFactory) {
        return new AsyncRabbitTemplate(new RabbitTemplate(connectionFactory));
    }

    @Bean
    public RabbitmqPublisher rabbitmqPublisher(RabbitmqOptions rabbitmqOptions, AsyncRabbitTemplate asyncRabbitTemplate) {
        return new RabbitmqPublisherImpl(rabbitmqOptions, asyncRabbitTemplate);
    }

    @Bean
    public RabbitmqReceiver rabbitmqReceiver(RabbitmqOptions rabbitmqOptions, ConnectionFactory connectionFactory) {
        return new RabbitmqReceiverImpl(rabbitmqOptions, connectionFactory);
    }
}