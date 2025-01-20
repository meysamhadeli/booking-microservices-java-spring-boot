package io.bookingmicroservices.passenger.passengers.features.createpassenger;

import buildingblocks.mediator.abstractions.commands.ICommandHandler;
import io.bookingmicroservices.passenger.data.jpa.repositories.PassengerRepository;
import io.bookingmicroservices.passenger.passengers.dtos.PassengerDto;
import io.bookingmicroservices.passenger.passengers.exceptions.PassengerAlreadyExistException;
import io.bookingmicroservices.passenger.passengers.features.Mappings;
import io.bookingmicroservices.passenger.passengers.models.Passenger;
import org.springframework.stereotype.Service;

@Service
public class CreatePassengerCommandHandler implements ICommandHandler<CreatePassengerCommand, PassengerDto> {
    private final PassengerRepository passengerRepository;

    public CreatePassengerCommandHandler(PassengerRepository passengerRepository) {
        this.passengerRepository = passengerRepository;
    }

    @Override
    public PassengerDto handle(CreatePassengerCommand command) {

        Passenger existPassenger = passengerRepository.findPassengerByPassportNumber(command.passportNumber());
        if (existPassenger != null) {
         throw new PassengerAlreadyExistException();
        }

        Passenger passengerAggregate = Mappings.toPassengerAggregate(command);
        Passenger passenger = passengerRepository.create(passengerAggregate);

        return Mappings.toPassengerDto(passenger);
    }
}
