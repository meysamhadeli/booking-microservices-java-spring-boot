package io.bookingmicroservices.flight.flights.exceptions;

import buildingblocks.core.exception.BadRequestException;

public class FlightExceptions extends BadRequestException {
    public FlightExceptions(String message) {
        super(message);
    }

    public static FlightExceptions departureBeforeArrival(String departureDate, String arriveDate) {
        return new FlightExceptions("Departure date: '" + departureDate + "' must be before arrive date: '" + arriveDate + "'.");
    }

    public static FlightExceptions invalidFlightDate(String flightDate) {
        return new FlightExceptions("Flight date: '" + flightDate + "' must be between departure and arrive dates.");
    }
}
