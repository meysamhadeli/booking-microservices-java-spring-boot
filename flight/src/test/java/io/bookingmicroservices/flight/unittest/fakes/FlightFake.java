package io.bookingmicroservices.flight.unittest.fakes;

import io.bookingmicroservices.flight.flights.features.Mappings;
import io.bookingmicroservices.flight.flights.features.createflight.CreateFlightCommand;
import io.bookingmicroservices.flight.flights.models.Flight;

public class FlightFake {
  public static Flight generate(){
   CreateFlightCommand command =  CreateFlightCommandFake.generate();
    return Mappings.toFlightAggregate(command);
  }
}
