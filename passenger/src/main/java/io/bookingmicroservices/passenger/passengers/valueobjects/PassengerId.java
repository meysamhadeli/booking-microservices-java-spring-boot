package io.bookingmicroservices.passenger.passengers.valueobjects;

import buildingblocks.utils.validation.ValidationUtils;
import java.util.UUID;

public record PassengerId(UUID value) {
    public PassengerId {
        ValidationUtils.notBeNullOrEmpty(value);
    }
}

