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
    Passenger findPassengerByPassportNumber(String passportNumber);
}

interface PassengerRepositoryCustom {
    Passenger create(Passenger passenger);
}

class PassengerRepositoryCustomImpl implements PassengerRepositoryCustom {

    private final EntityManager entityManager;

    PassengerRepositoryCustomImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public Passenger create(Passenger passenger) {
        PassengerEntity entity = Mappings.toPassengerEntity(passenger);

        entityManager.persist(entity);
        entityManager.flush();

        return Mappings.toPassengerAggregate(entity);
    }
}

