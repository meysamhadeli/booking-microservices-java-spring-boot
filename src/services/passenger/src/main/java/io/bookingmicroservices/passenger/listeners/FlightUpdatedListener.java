package io.bookingmicroservices.passenger.listeners;

import buildingblocks.contracts.flight.FlightUpdated;
import buildingblocks.rabbitmq.RabbitmqMessageHandler;
import buildingblocks.utils.jsonconverter.JsonConverterUtils;
import org.slf4j.Logger;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageListener;
import org.springframework.stereotype.Component;

@RabbitmqMessageHandler(messageType = FlightUpdated.class)
@Component
public class FlightUpdatedListener implements MessageListener {
  private final Logger logger;

  public FlightUpdatedListener(Logger logger) {
    this.logger = logger;
  }

  @Override
  public void onMessage(Message message) {
    FlightUpdated flightUpdated = JsonConverterUtils.deserialize(message.getBody(), FlightUpdated.class);
    logger.info("Do other processing after update flight in passenger service for this flight: {}", flightUpdated.toString());
  }
}
