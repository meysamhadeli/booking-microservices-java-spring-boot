package io.bookingmicroservices.flight.endtoendtest.fakes;

import com.github.f4b6a3.uuid.UuidCreator;
import io.bookingmicroservices.flight.flights.enums.FlightStatus;
import io.bookingmicroservices.flight.flights.features.createflight.CreateFlightCommand;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

public class CreateFlightCommandFake {
  public static CreateFlightCommand generate() {
    return new CreateFlightCommand(
      UuidCreator.getTimeOrderedEpoch(),
      "20H50",
      UUID.fromString("3c5c0000-97c6-fc34-2e11-08db322230c9"),
      UUID.fromString("3c5c0000-97c6-fc34-a0cb-08db322230c8"),
      LocalDateTime.now(),
      LocalDateTime.now(),
      UUID.fromString("3c5c0000-97c6-fc34-fc3c-08db322230c8"),
      new BigDecimal(120),
      LocalDateTime.now(),
      FlightStatus.Flying,
      new BigDecimal(200L)
    );
  }
}
