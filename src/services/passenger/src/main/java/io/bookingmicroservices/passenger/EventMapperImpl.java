package io.bookingmicroservices.passenger;

import buildingblocks.contracts.passenger.PassengerCreated;
import buildingblocks.core.event.DomainEvent;
import buildingblocks.core.event.EventMapper;
import buildingblocks.core.event.IntegrationEvent;
import buildingblocks.core.event.InternalCommand;
import io.bookingmicroservices.passenger.passengers.features.createpassenger.CreatePassengerMongoCommand;
import io.bookingmicroservices.passenger.passengers.features.createpassenger.PassengerCreatedDomainEvent;
import org.springframework.stereotype.Component;

@Component
public class EventMapperImpl implements EventMapper {
    @Override
    public IntegrationEvent MapToIntegrationEvent(DomainEvent event) {
        return switch (event) {
            case PassengerCreatedDomainEvent e -> new PassengerCreated(e.id());
            default -> null;
        };
    }

    @Override
    public InternalCommand MapToInternalCommand(DomainEvent event) {
        return switch (event) {
            case PassengerCreatedDomainEvent e -> new CreatePassengerMongoCommand(e.id(), e.name(), e.passportNumber(), e.passengerType(), e.age(), e.isDeleted());
            default -> null;
        };
    }
}
