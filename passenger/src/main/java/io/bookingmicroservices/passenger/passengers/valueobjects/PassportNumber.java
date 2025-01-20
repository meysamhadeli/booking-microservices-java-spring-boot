package io.bookingmicroservices.passenger.passengers.valueobjects;

import buildingblocks.utils.validation.ValidationUtils;

public record PassportNumber(String value) {
    public PassportNumber {
        ValidationUtils.notBeNullOrEmpty(value);
    }
}

