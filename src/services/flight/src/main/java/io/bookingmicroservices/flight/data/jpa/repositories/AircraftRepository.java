package io.bookingmicroservices.flight.data.jpa.repositories;

import io.bookingmicroservices.flight.aircrafts.features.Mappings;
import io.bookingmicroservices.flight.aircrafts.models.Aircraft;
import io.bookingmicroservices.flight.data.jpa.entities.AircraftEntity;
import jakarta.persistence.EntityManager;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface AircraftRepository extends JpaRepository<AircraftEntity, UUID> {
  AircraftEntity findAircraftByModelAndIsDeletedFalse(String model);
}
