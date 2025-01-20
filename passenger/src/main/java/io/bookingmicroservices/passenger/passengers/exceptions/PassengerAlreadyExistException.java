package io.bookingmicroservices.passenger.passengers.exceptions;

import buildingblocks.core.exception.ConflictException;

public class PassengerAlreadyExistException extends ConflictException {
    public PassengerAlreadyExistException() {
        super("Passenger already exists!");
    }
}

