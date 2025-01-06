package com.booking.microservices.java.spring.boot.flight.data.jpa.repositories;

import com.booking.microservices.java.spring.boot.flight.data.jpa.entities.FlightEntity;
import com.booking.microservices.java.spring.boot.flight.flights.exceptions.FlightAlreadyExistException;
import com.booking.microservices.java.spring.boot.flight.flights.models.Flight;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import jakarta.persistence.EntityManager;

import java.util.UUID;

@Repository
public interface FlightRepository extends JpaRepository<FlightEntity, UUID>, FlightRepositoryCustom {
}

@Repository
interface FlightRepositoryCustom {
  Flight create(Flight flight);
}

@Component
class FlightRepositoryCustomImpl implements FlightRepositoryCustom {

  private final EntityManager entityManager;

  FlightRepositoryCustomImpl(EntityManager entityManager) {
    this.entityManager = entityManager;
  }

  @Override
  public Flight create(Flight flight) {

    FlightEntity entity = FlightEntity.toEntity(flight);
    FlightEntity existingEntity = entityManager.find(FlightEntity.class, flight.getId().value());
    if (existingEntity != null) {
      throw new FlightAlreadyExistException();
    }
    entityManager.persist(entity);
    entityManager.flush();
    return entity.toAggregate();

  }
}
