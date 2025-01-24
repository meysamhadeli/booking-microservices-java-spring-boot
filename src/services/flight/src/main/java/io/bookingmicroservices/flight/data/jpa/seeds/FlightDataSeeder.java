package io.bookingmicroservices.flight.data.jpa.seeds;

import io.bookingmicroservices.flight.data.mongo.documents.AircraftDocument;
import io.bookingmicroservices.flight.data.mongo.documents.AirportDocument;
import io.bookingmicroservices.flight.data.mongo.documents.FlightDocument;
import io.bookingmicroservices.flight.data.mongo.documents.SeatDocument;
import static io.bookingmicroservices.flight.seats.features.Mappings.toSeatDocument;
import static io.bookingmicroservices.flight.flights.features.Mappings.toFlightDocument;
import static io.bookingmicroservices.flight.aircrafts.features.Mappings.toAircraftDocument;
import static io.bookingmicroservices.flight.airports.features.Mappings.toAirportDocument;
import jakarta.persistence.EntityManager;
import org.slf4j.Logger;
import org.springframework.boot.CommandLineRunner;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.support.TransactionTemplate;

@Component
public class FlightDataSeeder implements CommandLineRunner {

  private final EntityManager entityManager;
  private final TransactionTemplate transactionTemplate;
  private final MongoTemplate mongoTemplate;
  private final Logger logger;

  public FlightDataSeeder(
    EntityManager entityManager,
    PlatformTransactionManager platformTransactionManager,
    MongoTemplate mongoTemplate,
    Logger logger) {
    this.entityManager = entityManager;
    this.transactionTemplate = new TransactionTemplate(platformTransactionManager);

    this.mongoTemplate = mongoTemplate;
    this.logger = logger;
  }

  @Override
  public void run(String... args) throws Exception {
    transactionTemplate.execute(status -> {
      try {
        logger.info("Data seeder is started.");

        seedAirport();
        seedAircraft();
        seedFlight();
        seedSeat();

        logger.info("Data seeder is finished.");

        return null;
      }catch (Exception ex) {
        status.setRollbackOnly();
        logger.error(ex.getMessage(), ex);
        throw ex;
      }
    });
  }

  private void seedAirport() {
    if ((Long) entityManager.createQuery("SELECT COUNT(a) FROM AirportEntity a").getSingleResult() == 0) {
      InitialData.airports.forEach(entityManager::persist);

      if (mongoTemplate.getCollection("airports").countDocuments() == 0) {
        InitialData.airports.forEach(airport -> {
          AirportDocument airportDocument = toAirportDocument(airport);
          mongoTemplate.insert(airportDocument);
        });
      }
    }
  }

  private void seedAircraft() {
    if ((Long) entityManager.createQuery("SELECT COUNT(a) FROM AircraftEntity a").getSingleResult() == 0) {
        InitialData.aircrafts.forEach(entityManager::persist);

      if (mongoTemplate.getCollection("aircrafts").countDocuments() == 0) {
        InitialData.aircrafts.forEach(aircraft -> {
          AircraftDocument aircraftDocument = toAircraftDocument(aircraft);
          mongoTemplate.insert(aircraftDocument);
        });
      }
    }
  }

  private void seedFlight() {
    if ((Long) entityManager.createQuery("SELECT COUNT(f) FROM FlightEntity f").getSingleResult() == 0) {
      InitialData.flights.forEach(entityManager::persist);

      if (mongoTemplate.getCollection("flights").countDocuments() == 0) {
        InitialData.flights.forEach(flightEntity -> {
          FlightDocument flightDocument = toFlightDocument(flightEntity);
          mongoTemplate.insert(flightDocument);
        });
      }
    }
  }

  private void seedSeat() {
    if ((Long) entityManager.createQuery("SELECT COUNT(s) FROM SeatEntity s").getSingleResult() == 0) {
      InitialData.seats.forEach(entityManager::persist);

      if (mongoTemplate.getCollection("seats").countDocuments() == 0) {
        InitialData.seats.forEach(seatEntity -> {
          SeatDocument seatDocument = toSeatDocument(seatEntity);
          mongoTemplate.insert(seatDocument);
        });
      }
    }
  }
}
