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
  Flight findFlightByIdAndIsDeletedFalse(UUID id);
}


interface FlightRepositoryCustom {
  Flight create(Flight flight);
  Flight update(Flight flight);
  Flight delete(Flight flight);
}

class FlightRepositoryCustomImpl implements FlightRepositoryCustom {

  private final EntityManager entityManager;

  FlightRepositoryCustomImpl(EntityManager entityManager) {
    this.entityManager = entityManager;
  }

  @Override
  public Flight create(Flight flight) {
    FlightEntity entity = Mappings.toFlightEntity(flight);

    entityManager.persist(entity);
    entityManager.flush();

    return Mappings.toFlightAggregate(entity);
  }

  @Override
  public Flight update(Flight flight) {
    FlightEntity entity = Mappings.toFlightEntity(flight);

    entityManager.merge(entity);
    entityManager.flush();

    return Mappings.toFlightAggregate(entity);
  }

  @Override
  public Flight delete(Flight flight) {
    FlightEntity entity = Mappings.toFlightEntity(flight);

    entityManager.remove(entity);
    entityManager.flush();

    return Mappings.toFlightAggregate(entity);
  }
}
