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
    Booking create(Booking booking);
}

class BookingRepositoryCustomImpl implements BookingRepositoryCustom {

    private final EntityManager entityManager;

    BookingRepositoryCustomImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public Booking create(Booking booking) {
        BookingEntity entity = Mappings.toBookingEntity(booking);

        entityManager.persist(entity);
        entityManager.flush();

        return Mappings.toBookingAggregate(entity);
    }
}

