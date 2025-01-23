package io.bookingmicroservices.flight.data.jpa.entities;

import buildingblocks.core.model.BaseEntity;
import io.bookingmicroservices.flight.aircrafts.valueobjects.AircraftId;
import io.bookingmicroservices.flight.airports.valueobjects.AirportId;
import io.bookingmicroservices.flight.flights.enums.FlightStatus;
import io.bookingmicroservices.flight.flights.valueobjects.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "flights")
@EntityListeners(AuditingEntityListener.class)
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class FlightEntity extends BaseEntity<UUID> {

  @Embedded
  private FlightNumber flightNumber;

  @Embedded
  private DepartureDate departureDate;

  @Embedded
  private ArriveDate arriveDate;

  @Embedded
  private DurationMinutes durationMinutes;

  @Embedded
  private FlightDate flightDate;

  @Enumerated(EnumType.STRING)
  private FlightStatus status;

  @Embedded
  private Price price;

  @Embedded
  @AttributeOverride(name = "aircraftId", column = @Column(name = "aircraft_id"))
  private AircraftId aircraftId;

  @Embedded
  @AttributeOverride(name = "airportId", column = @Column(name = "departure_airport_id"))
  private AirportId departureAirportId;

  @Embedded
  @AttributeOverride(name = "airportId", column = @Column(name = "arrive_airport_id"))
  private AirportId arriveAirportId;

  public FlightEntity(UUID id, FlightNumber flightNumber, AircraftId aircraftId, AirportId departureAirportId, AirportId arriveAirportId, DurationMinutes durationMinutes, FlightStatus status, Price price, ArriveDate arriveDate, DepartureDate departureDate, FlightDate flightDate,
                      LocalDateTime createdAt, Long createdBy, LocalDateTime lastModified, Long lastModifiedBy, Long version, boolean isDeleted) {
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
    this.createdAt = createdAt;
    this.createdBy = createdBy;
    this.lastModified = lastModified;
    this.lastModifiedBy = lastModifiedBy;
    this.version = version;
    this.isDeleted = isDeleted;
  }

  public FlightEntity(UUID id, FlightNumber flightNumber, AircraftId aircraftId, AirportId departureAirportId, AirportId arriveAirportId, DurationMinutes durationMinutes, FlightStatus status, Price price, ArriveDate arriveDate, DepartureDate departureDate, FlightDate flightDate) {
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
