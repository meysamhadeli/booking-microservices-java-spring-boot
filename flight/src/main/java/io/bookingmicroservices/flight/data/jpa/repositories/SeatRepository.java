package io.bookingmicroservices.flight.data.jpa.repositories;

import io.bookingmicroservices.flight.data.jpa.entities.AircraftEntity;
import io.bookingmicroservices.flight.data.jpa.entities.SeatEntity;
import io.bookingmicroservices.flight.seats.features.Mappings;
import io.bookingmicroservices.flight.seats.models.Seat;
import jakarta.persistence.EntityManager;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.UUID;


@Repository
public interface SeatRepository extends JpaRepository<AircraftEntity, UUID>, SeatRepositoryCustom {
  boolean existsById(UUID id);
}

interface SeatRepositoryCustom {
  Seat create(Seat seat);
}

class SeatRepositoryCustomImpl implements SeatRepositoryCustom {

  private final EntityManager entityManager;

  SeatRepositoryCustomImpl(EntityManager entityManager) {
    this.entityManager = entityManager;
  }

  @Override
  public Seat create(Seat seat) {
    SeatEntity entity = Mappings.toSeatEntity(seat);

    entityManager.persist(entity);
    entityManager.flush();

    return Mappings.toSeatAggregate(entity);
  }
}

