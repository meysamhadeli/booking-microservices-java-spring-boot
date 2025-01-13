package io.bookingmicroservices.flight.flights.features.createflight;

import buildingblocks.core.exception.ValidationException;
import buildingblocks.jpa.JpaTransactionCoordinator;
import buildingblocks.mediator.abstractions.commands.ICommandHandler;
import io.bookingmicroservices.flight.data.jpa.repositories.FlightRepository;
import io.bookingmicroservices.flight.flights.dtos.FlightDto;
import io.bookingmicroservices.flight.flights.features.Mappings;
import io.bookingmicroservices.flight.flights.models.Flight;
import java.util.concurrent.CompletableFuture;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import org.springframework.validation.DataBinder;

@Service
public class CreateFlightCommandHandler implements ICommandHandler<CreateFlightCommand, CompletableFuture<FlightDto> > {
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

  @Override
  public CompletableFuture<FlightDto> handle(CreateFlightCommand command) throws Exception {
    //Create errors container
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
