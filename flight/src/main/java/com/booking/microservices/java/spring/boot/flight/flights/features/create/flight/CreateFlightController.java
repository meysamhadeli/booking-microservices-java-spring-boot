package com.booking.microservices.java.spring.boot.flight.flights.features.create.flight;

import com.booking.microservices.java.spring.boot.flight.flights.dtos.FlightDto;
import com.booking.microservices.java.spring.boot.flight.flights.features.create.Mappings;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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
  @PreAuthorize("hasAuthority('ADMIN')")
  public CompletableFuture<ResponseEntity<FlightDto>> createFlight(@RequestBody CreateFlightRequestDto createFlightRequestDto) {
    CreateFlightCommand command = Mappings.toCreateFlightCommand(createFlightRequestDto);
    return commandGateway.send(command);
  }
}
