package io.bookingmicroservices.flight.flights.features.deleteflight;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class DeleteFlightCommandValidator implements Validator {

  @Override
  public boolean supports(Class<?> clazz) {
    return DeleteFlightCommand.class.equals(clazz);
  }

  @Override
  public void validate(Object target, Errors errors) {
    DeleteFlightCommand command = (DeleteFlightCommand) target;

    if (command.id() == null) {
      errors.rejectValue("id", "id.required", "Flight ID is required");
    }
  }
}


