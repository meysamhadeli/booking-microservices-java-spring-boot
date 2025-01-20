package io.bookingmicroservices.flight.data.jpa.repositories;

import io.bookingmicroservices.flight.data.jpa.entities.SeatEntity;
import io.bookingmicroservices.flight.seats.features.Mappings;
import io.bookingmicroservices.flight.seats.models.Seat;
import jakarta.persistence.EntityManager;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.UUID;


@Repository
public interface SeatRepository extends JpaRepository<SeatEntity, UUID>, SeatRepositoryCustom {
  boolean existsById(UUID id);
  Seat findSeatByFlightIdAndSeatNumberAndIsDeletedFalse(UUID flightId, String seatNumber);
}

interface SeatRepositoryCustom {
  Seat create(Seat seat);
  Seat update(Seat seat);
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


  @Override
  public Seat update(Seat seat) {
    SeatEntity entity = Mappings.toSeatEntity(seat);

    entityManager.merge(entity);
    entityManager.flush();

    return Mappings.toSeatAggregate(entity);
  }
}

