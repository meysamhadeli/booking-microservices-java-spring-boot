package io.bookingmicroservices.flight.data.mongo.documents;

import io.bookingmicroservices.flight.seats.enums.SeatClass;
import io.bookingmicroservices.flight.seats.enums.SeatType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.util.UUID;

@Document(collection = "seats")
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class SeatDocument {
  @Id
  private ObjectId id;
  private UUID seatId;
  private String seatNumber;
  private SeatType type;
  private SeatClass seatClass;
  private UUID flightId;
  private boolean isDeleted;

  public SeatDocument(UUID seatId, String seatNumber, SeatType type, SeatClass seatClass, UUID flightId, boolean isDeleted) {
    this.seatId = seatId;
    this.seatNumber = seatNumber;
    this.type = type;
    this.seatClass = seatClass;
    this.flightId = flightId;
    this.isDeleted = isDeleted;
  }
}

