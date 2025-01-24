package io.bookingmicroservices.flight.flights.features.deleteflight;

import buildingblocks.mediator.abstractions.IMediator;
import io.bookingmicroservices.flight.flights.dtos.FlightDto;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.util.UUID;


@RestController
@RequestMapping(path = "api/v1/flight")
@Tag(name = "flight")
public class DeleteFlightController {

  private final IMediator mediator;

  public DeleteFlightController(IMediator mediator) {
    this.mediator = mediator;
  }

  @DeleteMapping("/{id}")
  @PreAuthorize("hasAuthority('ADMIN')")
  public ResponseEntity<FlightDto> deleteFlight(@PathVariable UUID id) {
    this.mediator.send(new DeleteFlightCommand(id));
    return ResponseEntity.noContent().build();
  }
}

