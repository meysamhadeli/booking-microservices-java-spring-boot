package io.bookingmicroservices.passenger.passengers.features.createpassenger;

import io.bookingmicroservices.passenger.passengers.enums.PassengerType;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import java.util.EnumSet;

@Component
public class CreatePassengerCommandValidator implements Validator {

    @Override
    public boolean supports(Class<?> clazz) {
        return CreatePassengerCommand.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        CreatePassengerCommand command = (CreatePassengerCommand) target;

        if (command == null) {
            errors.rejectValue("id", "id.required", "Passenger ID is required");
        }

        if (command.name() == null) {
            errors.rejectValue("name", "name.required", "Name is required");
        }

        if (command.age() <= 0) {
            errors.rejectValue("age", "age.invalid", "Age must be greater than 0");
        }

        if (command.passportNumber() == null ) {
            errors.rejectValue("passportNumber", "passportNumber.required", "PassportNumber is required");
        }


        if (command.passengerType() == null || !isValidPassengerType(command.passengerType())) {
            errors.rejectValue("passengerType", "passengerType.invalid",
                    "PassengerType must be Male, Female or Baby");
        }
    }

    private boolean isValidPassengerType(PassengerType passengerType) {
        return EnumSet.of(
                PassengerType.Baby,
                PassengerType.Female,
                PassengerType.Male
        ).contains(passengerType);
    }
}

