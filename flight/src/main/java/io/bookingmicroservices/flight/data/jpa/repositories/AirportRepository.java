package io.bookingmicroservices.flight.data.jpa.repositories;

import io.bookingmicroservices.flight.airports.features.Mappings;
import io.bookingmicroservices.flight.airports.models.Airport;
import io.bookingmicroservices.flight.data.jpa.entities.AirportEntity;
import jakarta.persistence.EntityManager;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.UUID;


@Repository
public interface AirportRepository extends JpaRepository<AirportEntity, UUID>, AirportRepositoryCustom {
  AirportEntity findAirportByCodeAndIsDeletedFalse(String code);
}


interface AirportRepositoryCustom {
  AirportEntity create(AirportEntity airport);
}

class AirportRepositoryCustomImpl implements AirportRepositoryCustom {

  private final EntityManager entityManager;

  AirportRepositoryCustomImpl(EntityManager entityManager) {
    this.entityManager = entityManager;
  }

  @Override
  public AirportEntity create(AirportEntity airport) {

    entityManager.persist(airport);
    entityManager.flush();

    return airport;
  }
}
