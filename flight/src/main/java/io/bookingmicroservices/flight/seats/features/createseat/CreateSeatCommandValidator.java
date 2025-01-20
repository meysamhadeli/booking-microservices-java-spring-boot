package io.bookingmicroservices.flight.seats.features.createseat;

import io.bookingmicroservices.flight.seats.enums.SeatClass;
import io.bookingmicroservices.flight.seats.enums.SeatType;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import java.util.EnumSet;

@Component
public class CreateSeatCommandValidator implements Validator {

  @Override
  public boolean supports(Class<?> clazz) {
    return CreateSeatCommand.class.equals(clazz);
  }

  @Override
  public void validate(Object target, Errors errors) {
    CreateSeatCommand command = (CreateSeatCommand) target;

    if (command.id() == null) {
      errors.rejectValue("id", "id.required", "Airport ID is required");
    }

    if (command.seatNumber() == null) {
      errors.rejectValue("seatNumber", "seatNumber.required", "SeatNumber is required");
    }

    if (command.seatType() == null || !isValidSeatType(command.seatType())) {
      errors.rejectValue("seatType", "seatType.invalid",
        "SeatType must be Window, Delay, Middle or Aisle");
    }

    if (command.seatClass() == null || !isValidSeatClass(command.seatClass())) {
      errors.rejectValue("seatClass", "seatClass.invalid",
        "SeatClass must be FirstClass, Business or Economy");
    }

    if (command.flightId() == null) {
      errors.rejectValue("flightId", "flightId.required", "FlightId is required");
    }
  }

  private boolean isValidSeatType(SeatType seatType) {
    return EnumSet.of(
      SeatType.Aisle,
      SeatType.Window,
      SeatType.Middle
    ).contains(seatType);
  }

  private boolean isValidSeatClass(SeatClass seatClass) {
    return EnumSet.of(
      SeatClass.FirstClass,
      SeatClass.Economy,
      SeatClass.Business
    ).contains(seatClass);
  }
}
