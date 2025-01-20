package io.bookingmicroservices.flight.flights.features.getavailableflights;

import buildingblocks.mediator.abstractions.IMediator;
import io.bookingmicroservices.flight.flights.dtos.FlightDto;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

@RestController
@RequestMapping(path = "api/v1/flight")
@Tag(name = "flight")
public class GetAvailableFlightsController {

  private final IMediator mediator;

  public GetAvailableFlightsController(IMediator mediator) {
    this.mediator = mediator;
  }

  @GetMapping()
  @PreAuthorize("hasAuthority('ADMIN')")
  public ResponseEntity<List<FlightDto>> getAvailableFlights() {
    List<FlightDto> result = this.mediator.send(new GetAvailableFlightsQuery());
    return ResponseEntity.ok().body(result);
  }
}

