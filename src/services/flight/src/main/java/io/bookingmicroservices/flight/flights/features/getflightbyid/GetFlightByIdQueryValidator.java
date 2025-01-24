package io.bookingmicroservices.flight.flights.features.getflightbyid;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class GetFlightByIdQueryValidator implements Validator {

  @Override
  public boolean supports(Class<?> clazz) {
    return GetFlightByIdQuery.class.equals(clazz);
  }

  @Override
  public void validate(Object target, Errors errors) {
    GetFlightByIdQuery query = (GetFlightByIdQuery) target;

    if (query.id() == null) {
      errors.rejectValue("id", "id.required", "Flight ID is required");
    }
  }
}


