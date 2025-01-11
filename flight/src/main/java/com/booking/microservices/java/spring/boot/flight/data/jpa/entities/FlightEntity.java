package com.booking.microservices.java.spring.boot.flight.data.jpa.entities;

import buildingblocks.core.model.BaseEntity;
import com.booking.microservices.java.spring.boot.flight.aircrafts.valueobjects.AircraftId;
import com.booking.microservices.java.spring.boot.flight.airports.valueobjects.AirportId;
import com.booking.microservices.java.spring.boot.flight.flights.enums.FlightStatus;
import com.booking.microservices.java.spring.boot.flight.flights.models.Flight;
import com.booking.microservices.java.spring.boot.flight.flights.valueobjects.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
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

    private UUID aircraftId;

    private UUID departureAirportId;

    private LocalDateTime departureDate;

    private LocalDateTime arriveDate;

    private UUID arriveAirportId;

    private BigDecimal durationMinutes;

    private LocalDateTime flightDate;

    @Enumerated(EnumType.STRING)
    private FlightStatus status;

    private BigDecimal price;

    public FlightEntity(UUID id, String flightNumber, UUID aircraftId, UUID departureAirportId, UUID arriveAirportId, BigDecimal durationMinutes, FlightStatus status, BigDecimal price, LocalDateTime arriveDate, LocalDateTime departureDate, LocalDateTime flightDate) {
        this.id = id;;
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

