package io.bookingmicroservices.flight.flights.features.updateflight;

import buildingblocks.mediator.abstractions.IMediator;
import io.bookingmicroservices.flight.flights.dtos.FlightDto;
import io.bookingmicroservices.flight.flights.features.Mappings;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;


@RestController
@RequestMapping(path = "api/v1/flight")
@Tag(name = "flight")
public class UpdateFlightController {

  private final IMediator mediator;

  public UpdateFlightController(IMediator mediator) {
    this.mediator = mediator;
  }

  @PutMapping("/{id}")
  @PreAuthorize("hasAuthority('ADMIN')")
  public ResponseEntity<FlightDto> updateFlight(@PathVariable UUID id, @RequestBody UpdateFlightRequestDto updateFlightRequestDto) {
    UpdateFlightCommand command = Mappings.toUpdateFlightCommand(id, updateFlightRequestDto);
    this.mediator.send(command);
    return ResponseEntity.noContent().build();
  }
}


