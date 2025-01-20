package io.bookingmicroservices.passenger.passengers.valueobjects;

import buildingblocks.utils.validation.ValidationUtils;

public record Age(int value) {
    public Age {
        ValidationUtils.notBeNullOrEmpty(value);
    }
}

