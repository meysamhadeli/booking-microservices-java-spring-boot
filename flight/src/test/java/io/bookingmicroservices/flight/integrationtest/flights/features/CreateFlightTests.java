package io.bookingmicroservices.flight.integrationtest.flights.features;

import buildingblocks.testbase.IntegrationTestBase;
import com.github.f4b6a3.uuid.UuidCreator;
import io.bookingmicroservices.flight.flights.dtos.FlightDto;
import io.bookingmicroservices.flight.flights.enums.FlightStatus;
import io.bookingmicroservices.flight.flights.features.createflight.CreateFlightCommand;
import org.junit.jupiter.api.Test;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class CreateFlightTests extends IntegrationTestBase {

  @Test
  void testCreateFlight_Success() {
    // Given
    CreateFlightCommand command = new CreateFlightCommand(
      UuidCreator.getTimeOrderedEpoch(), "20H50", UuidCreator.getTimeOrderedEpoch(), UuidCreator.getTimeOrderedEpoch(), LocalDateTime.now(),
      LocalDateTime.now(), UuidCreator.getTimeOrderedEpoch(), new BigDecimal(120), LocalDateTime.now(), FlightStatus.Flying, new BigDecimal(200L)
    );

    // When
    FlightDto result = this.fixture.getMediator().send(command);

    // Then
    assertThat(result).isNotNull();
    assertThat(result.flightNumber()).isEqualTo("20H50");
  }
}
