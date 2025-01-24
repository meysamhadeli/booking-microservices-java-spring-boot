package io.bookingmicroservices.flight.unittest.flights.features;

import buildingblocks.testbase.UnitTestBase;
import io.bookingmicroservices.flight.data.jpa.entities.FlightEntity;
import io.bookingmicroservices.flight.data.jpa.repositories.FlightRepository;
import io.bookingmicroservices.flight.flights.dtos.FlightDto;
import io.bookingmicroservices.flight.flights.exceptions.FlightAlreadyExistException;
import io.bookingmicroservices.flight.flights.features.createflight.CreateFlightCommand;
import io.bookingmicroservices.flight.flights.features.createflight.CreateFlightCommandHandler;
import io.bookingmicroservices.flight.unittest.fakes.CreateFlightCommandFake;
import io.bookingmicroservices.flight.unittest.fakes.FlightEntityFake;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class CreateFlightTests extends UnitTestBase {
  @Mock
  private FlightRepository flightRepository;

  @InjectMocks
  private CreateFlightCommandHandler createFlightCommandHandler;

  @Test
  public void should_throw_exception_when_flight_is_already_exist() {
    // Arrange
    CreateFlightCommand command = CreateFlightCommandFake.generate();
    when(flightRepository.findFlightByIdAndIsDeletedFalse(command.id())).thenReturn(FlightEntityFake.generate());

    // Act && Assert
    assertThrows(FlightAlreadyExistException.class, () -> {
      createFlightCommandHandler.handle(command);
    });
    verify(flightRepository, times(1)).findFlightByIdAndIsDeletedFalse(command.id()); // Verify interaction
    verify(flightRepository, times(0)).save(any(FlightEntity.class)); // Verify that create is not called
  }

  @Test
  public void should_return_valid_flight_dto_when_we_create_a_flight() {
    // Arrange
    CreateFlightCommand command = CreateFlightCommandFake.generate();
    when(flightRepository.findFlightByIdAndIsDeletedFalse(command.id())).thenReturn(null);
    when(flightRepository.save(any(FlightEntity.class))).thenReturn(FlightEntityFake.generate());

    // Act
    FlightDto result = createFlightCommandHandler.handle(command);

    // Assert
    assertNotNull(result);
    assertEquals(result.flightNumber(), command.flightNumber());
    verify(flightRepository, times(1)).findFlightByIdAndIsDeletedFalse(command.id());
    verify(flightRepository, times(1)).save(any(FlightEntity.class)); // Verify that create was called
  }
}
