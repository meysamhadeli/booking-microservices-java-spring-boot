package io.bookingmicroservices.booking.data.jpa.repositories;

import io.bookingmicroservices.booking.bookings.features.Mappings;
import io.bookingmicroservices.booking.bookings.modles.Booking;
import io.bookingmicroservices.booking.data.jpa.entities.BookingEntity;
import jakarta.persistence.EntityManager;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.UUID;


@Repository
public interface BookingRepository extends JpaRepository<BookingEntity, UUID> {
   BookingEntity findBookingByIdAndIsDeletedFalse(UUID id);
}
