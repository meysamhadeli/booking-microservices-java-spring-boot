package io.bookingmicroservices.flight.listeners;

import buildingblocks.contracts.flight.FlightCreated;
import buildingblocks.rabbitmq.RabbitmqMessageHandler;
import buildingblocks.utils.jsonconverter.JsonConverterUtils;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageListener;
import org.springframework.stereotype.Component;

@RabbitmqMessageHandler(messageType = FlightCreated.class, queueName = "flight_queue")
@Component
public class FlightCreatedListener implements MessageListener {
  @Override
  public void onMessage(Message message) {
    FlightCreated flightCreated = JsonConverterUtils.deserialize(message.getBody(), FlightCreated.class);
    System.out.println("Flight Message" + flightCreated.toString());
  }
}
