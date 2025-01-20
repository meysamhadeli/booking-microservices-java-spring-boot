package io.bookingmicroservices.flight.data.jpa.seeds;

import com.github.f4b6a3.uuid.UuidCreator;
import io.bookingmicroservices.flight.data.jpa.entities.AircraftEntity;
import io.bookingmicroservices.flight.data.jpa.entities.AirportEntity;
import io.bookingmicroservices.flight.data.jpa.entities.FlightEntity;
import io.bookingmicroservices.flight.data.jpa.entities.SeatEntity;
import io.bookingmicroservices.flight.flights.enums.FlightStatus;
import io.bookingmicroservices.flight.seats.enums.SeatClass;
import io.bookingmicroservices.flight.seats.enums.SeatType;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class InitialData {

  public static final List<FlightEntity> flights;
  public static final List<AirportEntity> airports;
  public static final List<AircraftEntity> aircrafts;
  public static final List<SeatEntity> seats;

  static  {
    airports = new ArrayList<>();
    airports.add(new AirportEntity(UUID.fromString("3c5c0000-97c6-fc34-a0cb-08db322230c8"), "Lisbon International Airport", "LIS", "12988"));
    airports.add(new AirportEntity(UUID.fromString("3c5c0000-97c6-fc34-fc3c-08db322230c8"), "Sao Paulo International Airport", "BRZ", "11200"));

    aircrafts = new ArrayList<>();
    aircrafts.add(new AircraftEntity(UUID.fromString("3c5c0000-97c6-fc34-fcd3-08db322230c8"), "Boeing 737", "B737", 2005));
    aircrafts.add(new AircraftEntity(UUID.fromString("3c5c0000-97c6-fc34-2e04-08db322230c9"), "Airbus 300", "A300", 2000));
    aircrafts.add(new AircraftEntity(UUID.fromString("3c5c0000-97c6-fc34-2e11-08db322230c9"), "Airbus 320", "A320", 2003));

    flights = new ArrayList<>();
    flights.add(new FlightEntity(UUID.fromString("3c5c0000-97c6-fc34-2eb9-08db322230c9"), "BD467",
      aircrafts.getFirst().getId(), airports.getFirst().getId(), airports.getLast().getId(), new BigDecimal(120), FlightStatus.Completed, new BigDecimal(8000), LocalDateTime.of(2022, 1, 31, 12, 0),
      LocalDateTime.of(2022, 1, 31, 14, 0),
      LocalDateTime.of(2022, 1, 31, 13, 0)));

    seats = new ArrayList<>();
    seats.add(new SeatEntity(UuidCreator.getTimeOrderedEpoch(), "12A", SeatType.Window, SeatClass.Economy, flights.get(0).getId()));
    seats.add(new SeatEntity(UuidCreator.getTimeOrderedEpoch(), "12B", SeatType.Window, SeatClass.Economy, flights.get(0).getId()));
    seats.add(new SeatEntity(UuidCreator.getTimeOrderedEpoch(), "12C", SeatType.Middle, SeatClass.Economy, flights.get(0).getId()));
    seats.add(new SeatEntity(UuidCreator.getTimeOrderedEpoch(), "12D", SeatType.Middle, SeatClass.Economy, flights.get(0).getId()));
    seats.add(new SeatEntity(UuidCreator.getTimeOrderedEpoch(), "12E", SeatType.Aisle, SeatClass.Economy, flights.get(0).getId()));
    seats.add(new SeatEntity(UuidCreator.getTimeOrderedEpoch(), "12F", SeatType.Aisle, SeatClass.Economy, flights.get(0).getId()));
  }
}
