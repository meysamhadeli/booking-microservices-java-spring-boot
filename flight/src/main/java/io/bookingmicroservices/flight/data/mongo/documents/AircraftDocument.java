package io.bookingmicroservices.flight.data.mongo.documents;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.UUID;


@Document(collection = "aircrafts")
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class AircraftDocument {
  @Id
  private ObjectId id;
  private UUID aircraftId;
  private String name;
  private String model;
  private int manufacturingYear;
  private boolean isDeleted;

  public AircraftDocument(UUID aircraftId, String name, String model, int manufacturingYear, boolean isDeleted) {
    this.aircraftId = aircraftId;
    this.name = name;
    this.model = model;
    this.manufacturingYear = manufacturingYear;
    this.isDeleted = isDeleted;
  }
}

