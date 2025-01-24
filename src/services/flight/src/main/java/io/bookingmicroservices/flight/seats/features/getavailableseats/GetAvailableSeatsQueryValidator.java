package io.bookingmicroservices.flight.seats.features.getavailableseats;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;


@Component
public class GetAvailableSeatsQueryValidator implements Validator {

  @Override
  public boolean supports(Class<?> clazz) {
    return GetAvailableSeatsQuery.class.equals(clazz);
  }

  @Override
  public void validate(Object target, Errors errors) {
    GetAvailableSeatsQuery query = (GetAvailableSeatsQuery) target;

    if (query.flightId() == null) {
      errors.rejectValue("flightId", "flightId.required", "Flight ID is required");
    }
  }
}

