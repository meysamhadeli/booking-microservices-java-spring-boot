package io.bookingmicroservices.flight.unittest.flights.features;

import buildingblocks.testbase.UnitTestBase;
import io.bookingmicroservices.flight.data.jpa.repositories.FlightRepository;
import io.bookingmicroservices.flight.flights.dtos.FlightDto;
import io.bookingmicroservices.flight.flights.exceptions.FlightAlreadyExistException;
import io.bookingmicroservices.flight.flights.features.createflight.CreateFlightCommand;
import io.bookingmicroservices.flight.flights.features.createflight.CreateFlightCommandHandler;
import io.bookingmicroservices.flight.flights.models.Flight;
import io.bookingmicroservices.flight.unittest.fakes.CreateFlightCommandFake;
import io.bookingmicroservices.flight.unittest.fakes.FlightFake;
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
    when(flightRepository.existsByFlightNumber(command.flightNumber())).thenReturn(true);

    // Act && Assert
    assertThrows(FlightAlreadyExistException.class, () -> {
      createFlightCommandHandler.handle(command);
    });
    verify(flightRepository, times(1)).existsByFlightNumber("20H50"); // Verify interaction
    verify(flightRepository, times(0)).create(any(Flight.class)); // Verify that create is not called
  }

  @Test
  public void should_return_valid_flight_dto_when_we_create_a_flight() {
    // Arrange
    CreateFlightCommand command = CreateFlightCommandFake.generate();
    when(flightRepository.existsByFlightNumber(command.flightNumber())).thenReturn(false);
    when(flightRepository.create(any(Flight.class))).thenReturn(FlightFake.generate());

    // Act
    FlightDto result = createFlightCommandHandler.handle(command);

    // Assert
    assertNotNull(result);
    assertEquals(result.flightNumber(), command.flightNumber());
    verify(flightRepository, times(1)).existsByFlightNumber(command.flightNumber());
    verify(flightRepository, times(1)).create(any(Flight.class)); // Verify that create was called
  }
}
