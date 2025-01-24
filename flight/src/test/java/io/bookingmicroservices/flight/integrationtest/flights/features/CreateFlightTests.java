package io.bookingmicroservices.flight.integrationtest.flights.features;

import buildingblocks.contracts.flight.FlightCreated;
import buildingblocks.testbase.TestBase;
import io.bookingmicroservices.flight.flights.dtos.FlightDto;
import io.bookingmicroservices.flight.flights.features.createflight.CreateFlightCommand;
import io.bookingmicroservices.flight.integrationtest.fakes.CreateFlightCommandFake;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class CreateFlightTests extends TestBase {

  @Test
  void should_create_new_flight_to_db_and_publish_message_to_broker() {
    //Arrange
    CreateFlightCommand command = CreateFlightCommandFake.generate();

    // Act
    FlightDto result = this.fixture.send(command);

    // Assert
    assertThat(result).isNotNull();
    assertThat(result.flightNumber()).isEqualTo(command.flightNumber());
    assertThat(this.fixture.messageIsPublished(FlightCreated.class)).isTrue();
  }
}
