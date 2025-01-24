package io.bookingmicroservices.booking.bookings.valueobjects;

import buildingblocks.utils.validation.ValidationUtils;
import jakarta.persistence.Embeddable;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import java.util.UUID;


@Embeddable
@EqualsAndHashCode
@NoArgsConstructor // Required by JPA
@Getter
public class BookingId {
    private UUID bookingId;

    public BookingId(UUID value) {
        ValidationUtils.notBeNullOrEmpty(value);

        this.bookingId = value;
    }
}

