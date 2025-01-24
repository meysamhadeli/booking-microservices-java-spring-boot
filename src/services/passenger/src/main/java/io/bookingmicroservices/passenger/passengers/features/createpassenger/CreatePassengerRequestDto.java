package io.bookingmicroservices.passenger.passengers.features.createpassenger;

import io.bookingmicroservices.passenger.passengers.enums.PassengerType;

public record CreatePassengerRequestDto(
        String name,
        String PassportNumber,
        PassengerType passengerType,
        int age){
}

