package io.bookingmicroservices.flight.data.jpa.repositories;

import io.bookingmicroservices.flight.aircrafts.features.Mappings;
import io.bookingmicroservices.flight.aircrafts.models.Aircraft;
import io.bookingmicroservices.flight.data.jpa.entities.AircraftEntity;
import jakarta.persistence.EntityManager;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface AircraftRepository extends JpaRepository<AircraftEntity, UUID>, AircraftRepositoryCustom {
  boolean existsByModel(String model);
}

interface AircraftRepositoryCustom {
  AircraftEntity create(AircraftEntity airport);
}

class AircraftRepositoryCustomImpl implements AircraftRepositoryCustom {

  private final EntityManager entityManager;

  AircraftRepositoryCustomImpl(EntityManager entityManager) {
    this.entityManager = entityManager;
  }

  @Override
  public AircraftEntity create(AircraftEntity aircraft) {

    entityManager.persist(aircraft);
    entityManager.flush();

    return aircraft;
  }
}

