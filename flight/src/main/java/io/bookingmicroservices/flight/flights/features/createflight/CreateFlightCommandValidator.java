package io.bookingmicroservices.flight.flights.features.createflight;

import io.bookingmicroservices.flight.flights.enums.FlightStatus;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import java.math.BigDecimal;
import java.util.EnumSet;

@Component
public class CreateFlightCommandValidator implements Validator {

  @Override
  public boolean supports(Class<?> clazz) {
    return CreateFlightCommand.class.equals(clazz);
  }

  @Override
  public void validate(Object target, Errors errors) {
    CreateFlightCommand command = (CreateFlightCommand) target;

    // Validate required fields
    if (command.id() == null) {
      errors.rejectValue("id", "id.required", "Flight ID is required");
    }

    if (command.flightNumber() == null || command.flightNumber().trim().isEmpty()) {
      errors.rejectValue("flightNumber", "flightNumber.required", "Flight number is required");
    }

    // Validate IDs
    if (command.aircraftId() == null) {
      errors.rejectValue("aircraftId", "aircraftId.required", "Aircraft ID is required");
    }

    if (command.departureAirportId() == null) {
      errors.rejectValue("departureAirportId", "departureAirportId.required", "Departure airport ID is required");
    }

    if (command.arriveAirportId() == null) {
      errors.rejectValue("arriveAirportId", "arriveAirportId.required", "Arrival airport ID is required");
    }

    // Validate dates
    if (command.departureDate() == null) {
      errors.rejectValue("departureDate", "departureDate.required", "Departure date is required");
    }

    if (command.arriveDate() == null) {
      errors.rejectValue("arriveDate", "arriveDate.required", "Arrival date is required");
    }

    // Validate date logic
    if (command.departureDate() != null && command.arriveDate() != null
      && command.departureDate().isAfter(command.arriveDate())) {
      errors.rejectValue("departureDate", "departureDate.invalid",
        "Departure date must be before arrival date");
    }

    // Validate price
    if (command.price() == null || command.price().compareTo(BigDecimal.ZERO) <= 0) {
      errors.rejectValue("price", "price.invalid", "Price must be greater than 0");
    }

    // Validate duration
    if (command.durationMinutes() == null || command.durationMinutes().compareTo(BigDecimal.ZERO) <= 0) {
      errors.rejectValue("durationMinutes", "durationMinutes.invalid",
        "Duration must be greater than 0");
    }

    // Validate status
    if (command.status() == null || !isValidStatus(command.status())) {
      errors.rejectValue("status", "status.invalid",
        "Status must be Flying, Delay, Canceled or Completed");
    }
  }

  private boolean isValidStatus(FlightStatus status) {
    return EnumSet.of(
      FlightStatus.Flying,
      FlightStatus.Canceled,
      FlightStatus.Delay,
      FlightStatus.Completed
    ).contains(status);
  }
}
