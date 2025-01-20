package io.bookingmicroservices.flight.seats.features.reserveseat;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;


@Component
public class ReserveSeatCommandValidator implements Validator {

  @Override
  public boolean supports(Class<?> clazz) {
    return ReserveSeatCommand.class.equals(clazz);
  }

  @Override
  public void validate(Object target, Errors errors) {
    ReserveSeatCommand command = (ReserveSeatCommand) target;

    if (command.seatNumber() == null) {
      errors.rejectValue("seatNumber", "seatNumber.required", "SeatNumber is required");
    }

    if (command.flightId() == null) {
      errors.rejectValue("flightId", "flightId.required", "FlightId is required");
    }
  }
}

