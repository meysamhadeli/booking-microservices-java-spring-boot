package io.bookingmicroservices.flight.data.mongo.documents;

import io.bookingmicroservices.flight.flights.enums.FlightStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Document(collection = "flights")
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class FlightDocument {

  @Id
  private ObjectId id;
  private UUID flightId;
  private String flightNumber;
  private UUID aircraftId;
  private UUID departureAirportId;
  private UUID arriveAirportId;
  private LocalDateTime departureDate;
  private LocalDateTime arriveDate;
  private BigDecimal durationMinutes;
  private LocalDateTime flightDate;
  private FlightStatus status;
  private BigDecimal price;
  private boolean isDeleted;

  public FlightDocument(ObjectId id, UUID flightId, String flightNumber, UUID aircraftId, UUID departureAirportId, UUID arriveAirportId, BigDecimal durationMinutes, FlightStatus status, BigDecimal price, LocalDateTime arriveDate, LocalDateTime departureDate, LocalDateTime flightDate, boolean isDeleted) {
    this.id = id;
    this.flightId = flightId;
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
    this.isDeleted = isDeleted;
  }

  public FlightDocument(UUID flightId, String flightNumber, UUID aircraftId, UUID departureAirportId, UUID arriveAirportId, BigDecimal durationMinutes, FlightStatus status, BigDecimal price, LocalDateTime arriveDate, LocalDateTime departureDate, LocalDateTime flightDate, boolean isDeleted) {
    this.flightId = flightId;
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
    this.isDeleted = isDeleted;
  }
}
