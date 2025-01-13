package io.bookingmicroservices.flight.data.jpa.repositories;

import io.bookingmicroservices.flight.data.jpa.entities.FlightEntity;
import io.bookingmicroservices.flight.flights.exceptions.FlightAlreadyExistException;
import io.bookingmicroservices.flight.flights.features.Mappings;
import io.bookingmicroservices.flight.flights.models.Flight;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import jakarta.persistence.EntityManager;
import java.util.UUID;

@Repository
public interface FlightRepository extends JpaRepository<FlightEntity, UUID>, FlightRepositoryCustom {
}


interface FlightRepositoryCustom {
  Flight create(Flight flight);
}

class FlightRepositoryCustomImpl implements FlightRepositoryCustom {

  private final EntityManager entityManager;

  FlightRepositoryCustomImpl(EntityManager entityManager) {
    this.entityManager = entityManager;
  }

  @Override
  public Flight create(Flight flight) {

    FlightEntity entity = Mappings.toFlightEntity(flight);
    FlightEntity existingEntity = entityManager.find(FlightEntity.class, flight.getId().value());
    if (existingEntity != null) {
      throw new FlightAlreadyExistException();
    }
    entityManager.persist(entity);

    return Mappings.toFlightAggregate(entity);
  }
}
