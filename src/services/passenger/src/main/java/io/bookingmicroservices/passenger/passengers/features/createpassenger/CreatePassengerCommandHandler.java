package io.bookingmicroservices.passenger.passengers.features.createpassenger;

import buildingblocks.mediator.abstractions.commands.ICommandHandler;
import io.bookingmicroservices.passenger.data.jpa.entities.PassengerEntity;
import io.bookingmicroservices.passenger.data.jpa.repositories.PassengerRepository;
import io.bookingmicroservices.passenger.passengers.dtos.PassengerDto;
import io.bookingmicroservices.passenger.passengers.exceptions.PassengerAlreadyExistException;
import io.bookingmicroservices.passenger.passengers.features.Mappings;
import io.bookingmicroservices.passenger.passengers.models.Passenger;
import io.bookingmicroservices.passenger.passengers.valueobjects.Age;
import io.bookingmicroservices.passenger.passengers.valueobjects.Name;
import io.bookingmicroservices.passenger.passengers.valueobjects.PassengerId;
import io.bookingmicroservices.passenger.passengers.valueobjects.PassportNumber;
import org.springframework.stereotype.Service;

@Service
public class CreatePassengerCommandHandler implements ICommandHandler<CreatePassengerCommand, PassengerDto> {
    private final PassengerRepository passengerRepository;

    public CreatePassengerCommandHandler(PassengerRepository passengerRepository) {
        this.passengerRepository = passengerRepository;
    }

    @Override
    public PassengerDto handle(CreatePassengerCommand command) {

        PassengerEntity existPassenger = passengerRepository.findPassengerByPassportNumberAndIsDeletedFalse(command.passportNumber());
        if (existPassenger != null) {
         throw new PassengerAlreadyExistException();
        }

        Passenger passengerAggregate = Passenger.create(
                new PassengerId(command.id()),
                new Name(command.name()),
                new PassportNumber(command.passportNumber()),
                command.passengerType(),
                new Age(command.age())
        );

        PassengerEntity passengerEntity = Mappings.toPassengerEntity(passengerAggregate);

        PassengerEntity createdPassenger = passengerRepository.save(passengerEntity);

        return Mappings.toPassengerDto(createdPassenger);
    }
}
