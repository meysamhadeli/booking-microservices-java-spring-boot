package io.bookingmicroservices.flight.seats.features.createseat;

import buildingblocks.mediator.abstractions.IMediator;
import io.bookingmicroservices.flight.seats.dtos.SeatDto;
import io.bookingmicroservices.flight.seats.features.Mappings;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping(path = "api/v1/flight/seat")
@Tag(name = "flight")
public class CreatSeatController {

  private final IMediator mediator;

  public CreatSeatController(IMediator mediator) {
    this.mediator = mediator;
  }

  @PostMapping()
  @PreAuthorize("hasAuthority('ADMIN')")
  public ResponseEntity<SeatDto> createAircraft(@RequestBody CreateSeatRequestDto createSeatRequestDto) {
    CreateSeatCommand command = Mappings.toCreateSeatCommand(createSeatRequestDto);
    var result = this.mediator.send(command);
    return ResponseEntity.ok().body(result);
  }
}

