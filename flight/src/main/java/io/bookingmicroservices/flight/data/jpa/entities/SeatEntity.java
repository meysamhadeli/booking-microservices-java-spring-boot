package io.bookingmicroservices.flight.data.jpa.entities;

import buildingblocks.core.model.BaseEntity;
import io.bookingmicroservices.flight.seats.enums.SeatClass;
import io.bookingmicroservices.flight.seats.enums.SeatType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import java.util.UUID;

@Entity
@Table(name = "seats")
@EntityListeners(AuditingEntityListener.class)
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class SeatEntity extends BaseEntity<UUID> {
  private String seatNumber;
  @Enumerated(EnumType.STRING)
  private SeatType type;
  @Enumerated(EnumType.STRING)
  private SeatClass seatClass;
  @Column(name = "flight_id")
  private UUID flightId;

  @ManyToOne
  @JoinColumn(name = "flight_id", insertable = false, updatable = false, nullable = false)
  private FlightEntity flight;

  public SeatEntity(UUID id, String seatNumber, SeatType type, SeatClass seatClass, UUID flightId) {
    this.id = id;
    this.seatNumber = seatNumber;
    this.type = type;
    this.seatClass = seatClass;
    this.flightId = flightId;
  }
}

