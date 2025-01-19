package io.bookingmicroservices.flight.aircrafts.features.createaircraft;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class CreateAircraftCommandValidator implements Validator {

  @Override
  public boolean supports(Class<?> clazz) {
    return CreateAircraftCommand.class.equals(clazz);
  }

  @Override
  public void validate(Object target, Errors errors) {
    CreateAircraftCommand command = (CreateAircraftCommand) target;

    if (command.id() == null) {
      errors.rejectValue("id", "id.required", "Airport ID is required");
    }

    if (command.name() == null) {
      errors.rejectValue("name", "name.required", "Name is required");
    }

    if (command.model() == null) {
      errors.rejectValue("model", "model.required", "Model is required");
    }

    if (command.manufacturingYear() >= 1900) {
      errors.rejectValue("manufacturingYear", "manufacturingYear.validate", "Manufacturing Year is not valid.");
    }
  }
}

