package io.bookingmicroservices.passenger.passengers.features.getpassengerbyid;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class GetPassengerByIdQueryValidator implements Validator {

    @Override
    public boolean supports(Class<?> clazz) {
        return GetPassengerByIdQuery.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        GetPassengerByIdQuery query = (GetPassengerByIdQuery) target;

        if (query.id() == null) {
            errors.rejectValue("id", "id.required", "Passenger ID is required");
        }
    }
}

