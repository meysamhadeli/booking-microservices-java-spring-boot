package io.bookingmicroservices.passenger.data.jpa.repositories;

import io.bookingmicroservices.passenger.data.jpa.entities.PassengerEntity;
import io.bookingmicroservices.passenger.passengers.features.Mappings;
import io.bookingmicroservices.passenger.passengers.models.Passenger;
import jakarta.persistence.EntityManager;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.UUID;


@Repository
public interface PassengerRepository extends JpaRepository<PassengerEntity, UUID>, PassengerRepositoryCustom {
    PassengerEntity findPassengerByPassportNumberAndIsDeletedFalse(String passportNumber);
}

interface PassengerRepositoryCustom {
    PassengerEntity create(PassengerEntity passenger);
}

class PassengerRepositoryCustomImpl implements PassengerRepositoryCustom {

    private final EntityManager entityManager;

    PassengerRepositoryCustomImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public PassengerEntity create(PassengerEntity passenger) {

        entityManager.persist(passenger);
        entityManager.flush();

        return passenger;
    }
}

