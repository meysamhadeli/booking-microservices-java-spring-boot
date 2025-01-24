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
public interface FlightRepository extends JpaRepository<FlightEntity, UUID> {
  FlightEntity findFlightByIdAndIsDeletedFalse(UUID id);
}
