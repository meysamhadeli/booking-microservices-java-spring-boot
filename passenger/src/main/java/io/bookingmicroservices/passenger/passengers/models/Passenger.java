package io.bookingmicroservices.passenger.passengers.models;

import buildingblocks.core.model.AggregateRoot;
import io.bookingmicroservices.passenger.passengers.enums.PassengerType;
import io.bookingmicroservices.passenger.passengers.features.createpassenger.PassengerCreatedDomainEvent;
import io.bookingmicroservices.passenger.passengers.valueobjects.Age;
import io.bookingmicroservices.passenger.passengers.valueobjects.Name;
import io.bookingmicroservices.passenger.passengers.valueobjects.PassengerId;
import io.bookingmicroservices.passenger.passengers.valueobjects.PassportNumber;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Getter
@Setter(AccessLevel.PRIVATE)
public class Passenger extends AggregateRoot<PassengerId> {
    Name name;
    PassportNumber passportNumber;
    PassengerType passengerType;
    Age age;
    boolean isDeleted;


    public Passenger(PassengerId passengerId, Name name, PassportNumber passportNumber, PassengerType passengerType, Age age, boolean isDeleted) {
       this.id = passengerId;
       this.name = name;
       this.passportNumber = passportNumber;
       this.passengerType = passengerType;
       this.age = age;
       this.isDeleted = isDeleted;
    }

    public static Passenger create(
            PassengerId passengerId,
            Name name,
            PassportNumber passportNumber,
            PassengerType passengerType,
            Age age,
            boolean isDeleted) {
        var passenger = new Passenger(passengerId, name, passportNumber, passengerType, age, isDeleted);

        passenger.addDomainEvent(new PassengerCreatedDomainEvent(
                passenger.id.value(),
                passenger.name.value(),
                passenger.passportNumber.value(),
                passenger.passengerType,
                passenger.age.value(),
                passenger.isDeleted
        ));

        return passenger;
    }
}
