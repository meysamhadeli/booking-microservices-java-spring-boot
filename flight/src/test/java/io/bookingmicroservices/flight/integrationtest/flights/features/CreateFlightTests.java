package io.bookingmicroservices.flight.integrationtest.flights.features;

import buildingblocks.contracts.flight.FlightCreated;
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
  void should_create_new_flight_to_db_and_publish_message_to_broker() {
    //Arrange
    CreateFlightCommand command = new CreateFlightCommand(
      UuidCreator.getTimeOrderedEpoch(), "20H50", UuidCreator.getTimeOrderedEpoch(), UuidCreator.getTimeOrderedEpoch(), LocalDateTime.now(),
      LocalDateTime.now(), UuidCreator.getTimeOrderedEpoch(), new BigDecimal(120), LocalDateTime.now(), FlightStatus.Flying, new BigDecimal(200L)
    );

    // Act
    FlightDto result = this.fixture.send(command);

    // Assert
    assertThat(result).isNotNull();
    assertThat(result.flightNumber()).isEqualTo("20H50");
    assertThat(this.fixture.messageIsPublished(FlightCreated.class)).isTrue();
  }
}
