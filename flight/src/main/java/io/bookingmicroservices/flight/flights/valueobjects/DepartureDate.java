package io.bookingmicroservices.flight.flights.valueobjects;

import buildingblocks.utils.validation.ValidationUtils;
import java.time.LocalDateTime;

public record DepartureDate(LocalDateTime value) {
    public DepartureDate {
      ValidationUtils.notBeNullOrEmpty(value);
    }
}
