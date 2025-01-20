package io.bookingmicroservices.passenger.passengers.features.createpassenger;

import buildingblocks.core.event.DomainEvent;
import io.bookingmicroservices.passenger.passengers.enums.PassengerType;
import java.util.UUID;

public record PassengerCreatedDomainEvent(
        UUID id,
        String name,
        String passportNumber,
        PassengerType passengerType,
        int age,
        boolean isDeleted) implements DomainEvent {
}