//package io.bookingmicroservices.flight.flights;
//
//import buildingblocks.rabbitmq.RabbitmqReceiver;
//import io.bookingmicroservices.flight.flights.features.createflight.SimpleMessageListener;
//import org.springframework.amqp.rabbit.listener.MessageListenerContainer;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
//import java.util.List;
//
//@Configuration
//public class ReceiverConfiguration {
//  @Bean
//  public MessageListenerContainer addListeners(RabbitmqReceiver receiver) {
//    return receiver.addListeners(List.of(new SimpleMessageListener()));
//  }
//}
