package io.bookingmicroservices.flight.seats.features.getavailableseats;

import buildingblocks.mediator.abstractions.IMediator;
import io.bookingmicroservices.flight.seats.dtos.SeatDto;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;
import java.util.UUID;


@RestController
@RequestMapping(path = "api/v1/flight/get-available-seats")
@Tag(name = "flight")
public class GetAvailableSeatsController {

  private final IMediator mediator;

  public GetAvailableSeatsController(IMediator mediator) {
    this.mediator = mediator;
  }

  @GetMapping("/{flightId}")
  @PreAuthorize("hasAuthority('ADMIN')")
  public ResponseEntity<List<SeatDto>> getAvailableSeats(@PathVariable UUID flightId) {
    List<SeatDto> result = this.mediator.send(new GetAvailableSeatsQuery(flightId));
    return ResponseEntity.ok().body(result);
  }
}

