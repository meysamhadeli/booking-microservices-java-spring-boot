package io.bookingmicroservices.booking.bookings.features;

import com.github.f4b6a3.uuid.UuidCreator;
import flight.Flight;
import io.bookingmicroservices.booking.bookings.dtos.BookingDto;
import io.bookingmicroservices.booking.bookings.features.createbooking.CreateBookingCommand;
import io.bookingmicroservices.booking.bookings.features.createbooking.CreateBookingMongoCommand;
import io.bookingmicroservices.booking.bookings.features.createbooking.CreateBookingRequestDto;
import io.bookingmicroservices.booking.bookings.modles.Booking;
import io.bookingmicroservices.booking.bookings.valueobjects.BookingId;
import io.bookingmicroservices.booking.data.jpa.entities.BookingEntity;
import io.bookingmicroservices.booking.data.mongo.documents.BookingDocument;


public final class Mappings {
    public static BookingEntity toBookingEntity(Booking booking) {
        return new BookingEntity(
                booking.getId().getBookingId(),
                booking.getPassengerInfo(),
                booking.getTrip(),
                booking.getCreatedAt(),
                booking.getCreatedBy(),
                booking.getLastModified(),
                booking.getLastModifiedBy(),
                booking.getVersion(),
                booking.isDeleted()
        );
    }

    public static BookingDto toBookingDto(BookingEntity booking) {
        return new BookingDto(
                booking.getId(),
                booking.getPassengerInfo().getName(),
                booking.getTrip().getFlightNumber(),
                booking.getTrip().getAircraftId(),
                booking.getTrip().getPrice(),
                booking.getTrip().getFlightDate(),
                booking.getTrip().getSeatNumber(),
                booking.getTrip().getDepartureAirportId(),
                booking.getTrip().getArriveAirportId(),
                booking.getTrip().getDescription()
        );
    }

    public static CreateBookingCommand toCreateBookingCommand(CreateBookingRequestDto bookingRequestDto) {
        return new CreateBookingCommand(
                UuidCreator.getTimeOrderedEpoch(),
                bookingRequestDto.passengerId(),
                bookingRequestDto.flightId(),
                bookingRequestDto.description()
        );
    }

    public static BookingDocument toBookingDocument(CreateBookingMongoCommand createBookingMongoCommand) {
        return new BookingDocument(
                createBookingMongoCommand.id(),
                createBookingMongoCommand.passengerInfo(),
                createBookingMongoCommand.trip(),
                createBookingMongoCommand.isDeleted()
        );
    }



    public static Flight.GetByIdRequestDto toFlightGetByIdRequestDto(CreateBookingCommand createBookingCommand) {
        return Flight.GetByIdRequestDto.newBuilder()
                .setId(createBookingCommand.flightId().toString()).build();
    }

    public static passenger.Passenger.PassengerRequestDto toPassengerGetByIdRequestDto(CreateBookingCommand createBookingCommand) {
        return passenger.Passenger.PassengerRequestDto.newBuilder()
                .setId(createBookingCommand.passengerId().toString()).build();
    }

    public static Flight.GetAvailableSeatsRequestDto toGetAvailableSeatsResponseDto(CreateBookingCommand createBookingCommand) {
        return flight.Flight.GetAvailableSeatsRequestDto.newBuilder()
                .setFlightId(createBookingCommand.flightId().toString()).build();
    }

    public static Flight.ReserveSeatRequestDto toReserveSeatRequestDto(String flightId, String seatNumber) {
        return Flight.ReserveSeatRequestDto.newBuilder()
                .setFlightId(flightId)
                .setSeatNumber(seatNumber)
                .build();
    }
}