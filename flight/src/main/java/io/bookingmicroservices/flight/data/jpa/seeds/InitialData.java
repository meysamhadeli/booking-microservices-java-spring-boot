package io.bookingmicroservices.flight.data.jpa.seeds;

import com.github.f4b6a3.uuid.UuidCreator;
import io.bookingmicroservices.flight.aircrafts.valueobjects.AircraftId;
import io.bookingmicroservices.flight.aircrafts.valueobjects.ManufacturingYear;
import io.bookingmicroservices.flight.aircrafts.valueobjects.Model;
import io.bookingmicroservices.flight.airports.valueobjects.Address;
import io.bookingmicroservices.flight.airports.valueobjects.AirportId;
import io.bookingmicroservices.flight.airports.valueobjects.Code;
import io.bookingmicroservices.flight.airports.valueobjects.Name;
import io.bookingmicroservices.flight.data.jpa.entities.AircraftEntity;
import io.bookingmicroservices.flight.data.jpa.entities.AirportEntity;
import io.bookingmicroservices.flight.data.jpa.entities.FlightEntity;
import io.bookingmicroservices.flight.data.jpa.entities.SeatEntity;
import io.bookingmicroservices.flight.flights.enums.FlightStatus;
import io.bookingmicroservices.flight.flights.valueobjects.*;
import io.bookingmicroservices.flight.seats.enums.SeatClass;
import io.bookingmicroservices.flight.seats.enums.SeatType;
import io.bookingmicroservices.flight.seats.valueobjects.FlightId;
import io.bookingmicroservices.flight.seats.valueobjects.SeatNumber;
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
    airports.add(new AirportEntity(UUID.fromString("3c5c0000-97c6-fc34-a0cb-08db322230c8"), new Name("Lisbon International Airport"), new Code("LIS"), new Address("12988")));
    airports.add(new AirportEntity(UUID.fromString("3c5c0000-97c6-fc34-fc3c-08db322230c8"), new Name("Sao Paulo International Airport"), new Code("BRZ"), new Address("11200")));

    aircrafts = new ArrayList<>();
    aircrafts.add(new AircraftEntity(UUID.fromString("3c5c0000-97c6-fc34-fcd3-08db322230c8"), new io.bookingmicroservices.flight.aircrafts.valueobjects.Name("Boeing 737"), new Model("B737"), new ManufacturingYear(2005)));
    aircrafts.add(new AircraftEntity(UUID.fromString("3c5c0000-97c6-fc34-2e04-08db322230c9"), new io.bookingmicroservices.flight.aircrafts.valueobjects.Name("Airbus 300"), new Model("A300"), new ManufacturingYear(2000)));
    aircrafts.add(new AircraftEntity(UUID.fromString("3c5c0000-97c6-fc34-2e11-08db322230c9"), new io.bookingmicroservices.flight.aircrafts.valueobjects.Name("Airbus 320"), new Model("A320"), new ManufacturingYear(2003)));

    flights = new ArrayList<>();
    flights.add(new FlightEntity(UUID.fromString("3c5c0000-97c6-fc34-2eb9-08db322230c9"), new FlightNumber("BD467"),
      new AircraftId(aircrafts.getFirst().getId()), new AirportId(airports.getFirst().getId()), new AirportId(airports.getLast().getId()), new DurationMinutes(new BigDecimal(120)), FlightStatus.Completed, new Price(new BigDecimal(8000)), new ArriveDate(LocalDateTime.of(2022, 1, 31, 12, 0)),
      new DepartureDate(LocalDateTime.of(2022, 1, 31, 14, 0)),
      new FlightDate(LocalDateTime.of(2022, 1, 31, 13, 0))));

    seats = new ArrayList<>();
    seats.add(new SeatEntity(UuidCreator.getTimeOrderedEpoch(), new SeatNumber("12A"), SeatType.Window, SeatClass.Economy, new FlightId(flights.get(0).getId())));
    seats.add(new SeatEntity(UuidCreator.getTimeOrderedEpoch(), new SeatNumber("12B"), SeatType.Window, SeatClass.Economy, new FlightId(flights.get(0).getId())));
    seats.add(new SeatEntity(UuidCreator.getTimeOrderedEpoch(), new SeatNumber("12C") , SeatType.Middle, SeatClass.Economy, new FlightId(flights.get(0).getId())));
    seats.add(new SeatEntity(UuidCreator.getTimeOrderedEpoch(), new SeatNumber("12D"), SeatType.Middle, SeatClass.Economy, new FlightId(flights.get(0).getId())));
    seats.add(new SeatEntity(UuidCreator.getTimeOrderedEpoch(), new SeatNumber("12E"), SeatType.Aisle, SeatClass.Economy, new FlightId(flights.get(0).getId())));
    seats.add(new SeatEntity(UuidCreator.getTimeOrderedEpoch(), new SeatNumber("12F"), SeatType.Aisle, SeatClass.Economy, new FlightId(flights.get(0).getId())));
  }
}
