package io.bookingmicroservices.passenger.listeners;

import buildingblocks.contracts.flight.FlightCreated;
import buildingblocks.rabbitmq.RabbitmqMessageHandler;
import buildingblocks.utils.jsonconverter.JsonConverterUtils;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageListener;
import org.springframework.stereotype.Component;

@RabbitmqMessageHandler(messageType = FlightCreated.class, queueName = "passenger_queue")
@Component
public class FlightCreatedListener implements MessageListener {
  @Override
  public void onMessage(Message message) {
    FlightCreated flightCreated = JsonConverterUtils.deserialize(message.getBody(), FlightCreated.class);
    System.out.println("Passenger Message:" + flightCreated.toString());
  }
}
