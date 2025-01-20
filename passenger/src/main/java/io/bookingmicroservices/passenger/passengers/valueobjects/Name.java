package io.bookingmicroservices.passenger.passengers.valueobjects;

import buildingblocks.utils.validation.ValidationUtils;

public record Name(String value) {
    public Name {
        ValidationUtils.notBeNullOrEmpty(value);
    }
}



