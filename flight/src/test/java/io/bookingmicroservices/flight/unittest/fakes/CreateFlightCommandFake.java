package io.bookingmicroservices.flight.unittest.fakes;

import com.github.f4b6a3.uuid.UuidCreator;
import io.bookingmicroservices.flight.flights.enums.FlightStatus;
import io.bookingmicroservices.flight.flights.features.createflight.CreateFlightCommand;
import java.math.BigDecimal;
import java.time.LocalDateTime;

public class CreateFlightCommandFake {
  public static CreateFlightCommand generate() {
    return new CreateFlightCommand(
      UuidCreator.getTimeOrderedEpoch(), "20H50", UuidCreator.getTimeOrderedEpoch(),
      UuidCreator.getTimeOrderedEpoch(), LocalDateTime.now(), LocalDateTime.now(),
      UuidCreator.getTimeOrderedEpoch(), new BigDecimal(120), LocalDateTime.now(),
      FlightStatus.Flying, new BigDecimal(200L)
    );
  }
}
