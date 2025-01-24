package io.bookingmicroservices.flight.unittest.fakes;

import io.bookingmicroservices.flight.data.jpa.entities.FlightEntity;
import io.bookingmicroservices.flight.flights.features.Mappings;
import io.bookingmicroservices.flight.flights.features.createflight.CreateFlightCommand;
import io.bookingmicroservices.flight.flights.models.Flight;

public class FlightEntityFake {
  public static FlightEntity generate(){
   CreateFlightCommand command =  CreateFlightCommandFake.generate();
    return Mappings.toFlightEntity(command);
  }
}
