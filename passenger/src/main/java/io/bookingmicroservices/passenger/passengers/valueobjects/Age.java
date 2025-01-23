package io.bookingmicroservices.passenger.passengers.valueobjects;

import buildingblocks.utils.validation.ValidationUtils;
import jakarta.persistence.Embeddable;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Embeddable
@EqualsAndHashCode
@NoArgsConstructor // Required by JPA
@Getter
public class Age {
    private int age;

    public Age(int value) {
        ValidationUtils.notBeNullOrEmpty(value);

        this.age = value;
    }
}



