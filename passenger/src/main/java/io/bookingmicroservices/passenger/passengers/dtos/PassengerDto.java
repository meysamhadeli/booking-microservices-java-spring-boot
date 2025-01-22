package io.bookingmicroservices.passenger.passengers.dtos;

import io.bookingmicroservices.passenger.passengers.enums.PassengerType;
import java.util.UUID;

public record PassengerDto(
        UUID id,
        String name,
        String passportNumber,
        PassengerType passengerType,
        int age
) { }