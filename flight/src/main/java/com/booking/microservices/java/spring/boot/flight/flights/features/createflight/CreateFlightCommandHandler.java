package com.booking.microservices.java.spring.boot.flight.flights.features.createflight;

import buildingblocks.core.exception.ValidationException;
import com.booking.microservices.java.spring.boot.flight.data.jpa.repositories.FlightRepository;
import com.booking.microservices.java.spring.boot.flight.flights.dtos.FlightDto;
import com.booking.microservices.java.spring.boot.flight.flights.features.Mappings;
import com.booking.microservices.java.spring.boot.flight.flights.models.Flight;
import buildingblocks.jpa.JpaTransactionCoordinator;
import org.axonframework.commandhandling.CommandHandler;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import java.util.concurrent.CompletableFuture;
import org.springframework.validation.BindingResult;
import org.springframework.validation.DataBinder;

@Component
public class CreateFlightCommandHandler {
  private final FlightRepository flightRepository;
  private final CreateFlightCommandValidator validator;
  private final JpaTransactionCoordinator jpaTransactionCoordinator;

  public CreateFlightCommandHandler(
    FlightRepository flightRepository,
    CreateFlightCommandValidator validator,
    JpaTransactionCoordinator jpaTransactionCoordinator) {
    this.flightRepository = flightRepository;
    this.validator = validator;
    this.jpaTransactionCoordinator = jpaTransactionCoordinator;
  }

  @CommandHandler
  @Async
  public CompletableFuture<FlightDto> handle(CreateFlightCommand command) {
    // Create errors container
    DataBinder dataBinder = new DataBinder(command);
    dataBinder.setValidator(validator);

    // Perform validation
    dataBinder.validate();
    BindingResult bindingResult = dataBinder.getBindingResult();

    if (bindingResult.hasErrors()) {
      throw new ValidationException(bindingResult.getAllErrors().getFirst().getDefaultMessage());
    }

    return CompletableFuture.supplyAsync(() -> {
      var result = jpaTransactionCoordinator.executeWithEvents(() -> {
        Flight flight = Mappings.toFlightAggregate(command);
        return flightRepository.create(flight);
      }, command.getClass());
      return Mappings.toFlightDto(result);
    });
  }
}
