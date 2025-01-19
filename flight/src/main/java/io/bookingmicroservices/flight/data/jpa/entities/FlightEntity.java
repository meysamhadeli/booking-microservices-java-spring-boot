package io.bookingmicroservices.flight.data.jpa.entities;

import buildingblocks.core.model.BaseEntity;
import io.bookingmicroservices.flight.flights.enums.FlightStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "flights")
@EntityListeners(AuditingEntityListener.class)
@NoArgsConstructor // Required by JPA
@AllArgsConstructor
@Getter
public class FlightEntity extends BaseEntity<UUID> {

  private String flightNumber;

  private LocalDateTime departureDate;

  private LocalDateTime arriveDate;

  private BigDecimal durationMinutes;

  private LocalDateTime flightDate;

  @Enumerated(EnumType.STRING)
  private FlightStatus status;

  private BigDecimal price;

  @Column(name = "departure_airport_id")
  private UUID departureAirportId;

  @Column(name = "arrive_airport_id")
  private UUID arriveAirportId;

  @Column(name = "aircraft_id")
  private UUID aircraftId;

  // JPA will handle the relationship based on these IDs
  @ManyToOne
  @JoinColumn(name = "departure_airport_id", insertable = false, updatable = false, nullable = false)
  private AirportEntity departureAirport;

  @ManyToOne
  @JoinColumn(name = "arrive_airport_id", insertable = false, updatable = false, nullable = false)
  private AirportEntity arriveAirport;

  @ManyToOne
  @JoinColumn(name = "aircraft_id", insertable = false, updatable = false, nullable = false)
  private AircraftEntity aircraft;

  // Constructor that uses only the ID types
  public FlightEntity(UUID id, String flightNumber, UUID aircraftId, UUID departureAirportId, UUID arriveAirportId, BigDecimal durationMinutes, FlightStatus status, BigDecimal price, LocalDateTime arriveDate, LocalDateTime departureDate, LocalDateTime flightDate) {
    this.id = id;
    this.flightNumber = flightNumber;
    this.aircraftId = aircraftId;
    this.departureAirportId = departureAirportId;
    this.arriveAirportId = arriveAirportId;
    this.durationMinutes = durationMinutes;
    this.status = status;
    this.price = price;
    this.arriveDate = arriveDate;
    this.departureDate = departureDate;
    this.flightDate = flightDate;
  }
}
