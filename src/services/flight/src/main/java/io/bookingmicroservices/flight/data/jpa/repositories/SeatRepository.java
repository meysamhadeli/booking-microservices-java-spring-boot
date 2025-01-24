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
public interface SeatRepository extends JpaRepository<SeatEntity, UUID> {
  SeatEntity findSeatByIdAndIsDeletedFalse(UUID id);
  SeatEntity findSeatByFlightIdAndSeatNumberAndIsDeletedFalse(FlightId flightId, SeatNumber seatNumber);
}
