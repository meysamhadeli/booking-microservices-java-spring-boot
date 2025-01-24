package io.bookingmicroservices.flight.data.jpa.repositories;

import io.bookingmicroservices.flight.data.jpa.entities.SeatEntity;
import io.bookingmicroservices.flight.seats.features.Mappings;
import io.bookingmicroservices.flight.seats.models.Seat;
import io.bookingmicroservices.flight.seats.valueobjects.FlightId;
import io.bookingmicroservices.flight.seats.valueobjects.SeatNumber;
import jakarta.persistence.EntityManager;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.UUID;


@Repository
public interface SeatRepository extends JpaRepository<SeatEntity, UUID>, SeatRepositoryCustom {
  SeatEntity findSeatByIdAndIsDeletedFalse(UUID id);
  SeatEntity findSeatByFlightIdAndSeatNumberAndIsDeletedFalse(FlightId flightId, SeatNumber seatNumber);
}

interface SeatRepositoryCustom {
  SeatEntity create(SeatEntity seat);
  SeatEntity update(SeatEntity seat);
}

class SeatRepositoryCustomImpl implements SeatRepositoryCustom {

  private final EntityManager entityManager;

  SeatRepositoryCustomImpl(EntityManager entityManager) {
    this.entityManager = entityManager;
  }

  @Override
  public SeatEntity create(SeatEntity seat) {

    entityManager.persist(seat);
    entityManager.flush();

    return seat;
  }


  @Override
  public SeatEntity update(SeatEntity seat) {

    entityManager.merge(seat);
    entityManager.flush();

    return seat;
  }
}

