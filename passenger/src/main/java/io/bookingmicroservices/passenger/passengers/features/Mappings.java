package io.bookingmicroservices.passenger.passengers.features;

import com.github.f4b6a3.uuid.UuidCreator;
import io.bookingmicroservices.passenger.data.jpa.entities.PassengerEntity;
import io.bookingmicroservices.passenger.data.mongo.documents.PassengerDocument;
import io.bookingmicroservices.passenger.passengers.dtos.PassengerDto;
import io.bookingmicroservices.passenger.passengers.features.createpassenger.CreatePassengerCommand;
import io.bookingmicroservices.passenger.passengers.features.createpassenger.CreatePassengerMongoCommand;
import io.bookingmicroservices.passenger.passengers.features.createpassenger.CreatePassengerRequestDto;
import io.bookingmicroservices.passenger.passengers.models.Passenger;
import io.bookingmicroservices.passenger.passengers.valueobjects.Age;
import io.bookingmicroservices.passenger.passengers.valueobjects.Name;
import io.bookingmicroservices.passenger.passengers.valueobjects.PassengerId;
import io.bookingmicroservices.passenger.passengers.valueobjects.PassportNumber;

public final class Mappings {

    public static PassengerEntity toPassengerEntity(Passenger passenger) {
        return new PassengerEntity(
                passenger.getId().value(),
                passenger.getName().value(),
                passenger.getPassportNumber().value(),
                passenger.getPassengerType(),
                passenger.getAge().value()
        );
    }

    public static Passenger toPassengerAggregate(PassengerEntity passengerEntity) {
        return new Passenger(
                new PassengerId(passengerEntity.getId()),
                new Name(passengerEntity.getName()),
                new PassportNumber(passengerEntity.getPassportNumber()),
                passengerEntity.getPassengerType(),
                new Age(passengerEntity.getAge()),
                passengerEntity.isDeleted()
        );
    }

    public static Passenger toPassengerAggregate(CreatePassengerCommand createPassengerCommand) {
        return Passenger.create(
                new PassengerId(createPassengerCommand.id()),
                new Name(createPassengerCommand.name()),
                new PassportNumber(createPassengerCommand.passportNumber()),
                createPassengerCommand.passengerType(),
                new Age(createPassengerCommand.age()),
                false
        );
    }


    public static PassengerDto toPassengerDto(Passenger passenger) {
        return new PassengerDto(
                passenger.getId().value(),
                passenger.getName().value(),
                passenger.getPassportNumber().value(),
                passenger.getPassengerType(),
                passenger.getAge().value());
    }


    public static PassengerDocument toPassengerDocument(CreatePassengerMongoCommand createPassengerMongoCommand) {
        return new PassengerDocument(
                createPassengerMongoCommand.id(),
                createPassengerMongoCommand.name(),
                createPassengerMongoCommand.passportNumber(),
                createPassengerMongoCommand.passengerType(),
                createPassengerMongoCommand.age(),
                createPassengerMongoCommand.isDeleted()
        );
    }

    public static CreatePassengerCommand toCreatePassengerCommand(CreatePassengerRequestDto passengerRequestDto) {
        return new CreatePassengerCommand(
                UuidCreator.getTimeOrderedEpoch(),
                passengerRequestDto.name(),
                passengerRequestDto.PassportNumber(),
                passengerRequestDto.passengerType(),
                passengerRequestDto.age()
        );
    }


    public static PassengerDto toPassengerDto(PassengerDocument passengerDocument) {
        return new PassengerDto(
                passengerDocument.getPassengerId(),
                passengerDocument.getName(),
                passengerDocument.getPassportNumber(),
                passengerDocument.getPassengerType(),
                passengerDocument.getAge()
        );
    }
}