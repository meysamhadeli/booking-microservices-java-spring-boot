package io.bookingmicroservices.booking.data.jpa.repositories;

import io.bookingmicroservices.booking.bookings.features.Mappings;
import io.bookingmicroservices.booking.bookings.modles.Booking;
import io.bookingmicroservices.booking.data.jpa.entities.BookingEntity;
import jakarta.persistence.EntityManager;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.UUID;


@Repository
public interface BookingRepository extends JpaRepository<BookingEntity, UUID>, BookingRepositoryCustom {
   BookingEntity findBookingByIdAndIsDeletedFalse(UUID id);
}

interface BookingRepositoryCustom {
    BookingEntity create(BookingEntity booking);
}

class BookingRepositoryCustomImpl implements BookingRepositoryCustom {

    private final EntityManager entityManager;

    BookingRepositoryCustomImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public BookingEntity create(BookingEntity booking) {

        entityManager.persist(booking);
        entityManager.flush();

        return booking;
    }
}

