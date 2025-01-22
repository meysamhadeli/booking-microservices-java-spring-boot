package io.bookingmicroservices.booking.bookings.features.createbooking;

import buildingblocks.mediator.abstractions.IMediator;
import io.bookingmicroservices.booking.bookings.dtos.BookingDto;
import io.bookingmicroservices.booking.bookings.features.Mappings;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping(path = "api/v1/booking")
@Tag(name = "booking")
public class CreateBookingController {

    private final IMediator mediator;

    public CreateBookingController(IMediator mediator) {
        this.mediator = mediator;
    }

    @PostMapping()
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<BookingDto> createBooking(@RequestBody CreateBookingRequestDto createAirportRequestDto) {
        CreateBookingCommand command = Mappings.toCreateBookingCommand(createAirportRequestDto);
        var result = this.mediator.send(command);
        return ResponseEntity.ok().body(result);
    }
}

