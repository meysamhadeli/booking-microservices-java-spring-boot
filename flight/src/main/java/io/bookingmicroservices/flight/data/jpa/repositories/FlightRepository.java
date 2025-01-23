package io.bookingmicroservices.flight.data.jpa.repositories;

import io.bookingmicroservices.flight.data.jpa.entities.FlightEntity;
import io.bookingmicroservices.flight.flights.features.Mappings;
import io.bookingmicroservices.flight.flights.models.Flight;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import jakarta.persistence.EntityManager;

import java.util.List;
import java.util.UUID;

@Repository
public interface FlightRepository extends JpaRepository<FlightEntity, UUID>, FlightRepositoryCustom {
  boolean existsByFlightNumber(String flightNumber);
  FlightEntity findFlightByIdAndIsDeletedFalse(UUID id);
}


interface FlightRepositoryCustom {
  FlightEntity create(FlightEntity flight);
  FlightEntity update(FlightEntity flight);
  FlightEntity remove(FlightEntity flight);
}

class FlightRepositoryCustomImpl implements FlightRepositoryCustom {

  private final EntityManager entityManager;

  FlightRepositoryCustomImpl(EntityManager entityManager) {
    this.entityManager = entityManager;
  }

  @Override
  public FlightEntity create(FlightEntity flight) {

    entityManager.persist(flight);
    entityManager.flush();

    return flight;
  }

  @Override
  public FlightEntity update(FlightEntity flight) {

    entityManager.merge(flight);
    entityManager.flush();

    return flight;
  }

  @Override
  public FlightEntity remove(FlightEntity flight) {

    entityManager.remove(flight);
    entityManager.flush();

    return flight;
  }
}
