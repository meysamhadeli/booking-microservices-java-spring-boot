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
  Aircraft create(Aircraft airport);
}

class AircraftRepositoryCustomImpl implements AircraftRepositoryCustom {

  private final EntityManager entityManager;

  AircraftRepositoryCustomImpl(EntityManager entityManager) {
    this.entityManager = entityManager;
  }

  @Override
  public Aircraft create(Aircraft aircraft) {
    AircraftEntity entity = Mappings.toAircraftEntity(aircraft);

    entityManager.persist(entity);
    entityManager.flush();

    return Mappings.toAircraftAggregate(entity);
  }
}

