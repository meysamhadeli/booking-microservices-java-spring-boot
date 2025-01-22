package io.bookingmicroservices.booking;

import buildingblocks.contracts.booking.BookingCreated;
import buildingblocks.core.event.DomainEvent;
import buildingblocks.core.event.EventMapper;
import buildingblocks.core.event.IntegrationEvent;
import buildingblocks.core.event.InternalCommand;
import io.bookingmicroservices.booking.bookings.features.createbooking.BookingCreatedDomainEvent;
import io.bookingmicroservices.booking.bookings.features.createbooking.CreateBookingMongoCommand;
import org.springframework.stereotype.Component;

@Component
public class EventMapperImpl implements EventMapper {
    @Override
    public IntegrationEvent MapToIntegrationEvent(DomainEvent event) {
        return switch (event) {
            case BookingCreatedDomainEvent e -> new BookingCreated(e.id());
            default -> null;
        };
    }

    @Override
    public InternalCommand MapToInternalCommand(DomainEvent event) {
        return switch (event) {
           case BookingCreatedDomainEvent e -> new CreateBookingMongoCommand(e.id(), e.passengerInfo(), e.trip(), e.isDeleted());
            default -> null;
        };
    }
}

