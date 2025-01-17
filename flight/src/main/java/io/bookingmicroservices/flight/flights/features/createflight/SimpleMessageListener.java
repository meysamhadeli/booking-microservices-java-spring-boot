package io.bookingmicroservices.flight.flights.features.createflight;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageListener;
import org.springframework.stereotype.Component;

@Component
public class SimpleMessageListener implements MessageListener {
  @Override
  public void onMessage(Message message) {
    System.out.println(message);
  }
}
