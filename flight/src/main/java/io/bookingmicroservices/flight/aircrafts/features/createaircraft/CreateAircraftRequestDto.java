package io.bookingmicroservices.flight.aircrafts.features.createaircraft;

public record CreateAircraftRequestDto(
  String name,
  String model,
  int manufacturingYear){
}
