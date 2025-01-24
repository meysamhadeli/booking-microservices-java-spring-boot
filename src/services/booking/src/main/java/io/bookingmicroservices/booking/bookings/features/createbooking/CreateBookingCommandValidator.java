package io.bookingmicroservices.booking.bookings.features.createbooking;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;


@Component
public class CreateBookingCommandValidator implements Validator {

    @Override
    public boolean supports(Class<?> clazz) {
        return CreateBookingCommand.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        CreateBookingCommand command = (CreateBookingCommand) target;

        if (command.flightId() == null) {
            errors.rejectValue("flightId", "flightId.required", "Flight ID is required");
        }

        if (command.passengerId() == null) {
            errors.rejectValue("passengerId", "passengerId.required", "Passenger ID is required");
        }
    }
}


