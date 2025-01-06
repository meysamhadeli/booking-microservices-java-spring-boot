package com.booking.microservices.java.spring.boot.flight.flights.features.create.flight;

import com.booking.microservices.java.spring.boot.flight.flights.features.create.Mappings;
import com.github.f4b6a3.uuid.UuidCreator;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping(path = "api/v1/flight")
public class CreateFlightController {

  private final CommandGateway commandGateway;

  public CreateFlightController(CommandGateway commandGateway) {
    this.commandGateway = commandGateway;
  }

  @PostMapping()
  public CompletableFuture<Void> createFlight(@RequestBody CreateFlightRequestDto createFlightRequestDto) {
    CreateFlightCommand command = Mappings.toCreateFlightCommand(createFlightRequestDto);
    return commandGateway.send(command).thenAcceptAsync(students -> new ResponseEntity<>(students, HttpStatus.OK));
  }
}
