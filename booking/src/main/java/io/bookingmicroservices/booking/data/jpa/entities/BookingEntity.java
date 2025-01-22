package io.bookingmicroservices.booking.data.jpa.entities;

import buildingblocks.core.model.BaseEntity;
import io.bookingmicroservices.booking.bookings.valueobjects.PassengerInfo;
import io.bookingmicroservices.booking.bookings.valueobjects.Trip;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import java.util.UUID;


@Entity
@Table(name = "bookings")
@EntityListeners(AuditingEntityListener.class)
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class BookingEntity extends BaseEntity<UUID> {

    @Embedded
    private PassengerInfo passengerInfo;
    @Embedded
    private Trip trip;
    private boolean isDeleted;

    public BookingEntity(UUID id, PassengerInfo passengerInfo, Trip trip) {
        this.id = id;
        this.passengerInfo = passengerInfo;
        this.trip = trip;
    }
}
