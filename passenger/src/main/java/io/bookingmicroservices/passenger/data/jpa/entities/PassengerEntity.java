package io.bookingmicroservices.passenger.data.jpa.entities;

import buildingblocks.core.model.BaseEntity;
import io.bookingmicroservices.passenger.passengers.enums.PassengerType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import java.util.UUID;


@Entity
@Table(name = "passengers")
@EntityListeners(AuditingEntityListener.class)
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class PassengerEntity extends BaseEntity<UUID> {

    private String name;
    private String passportNumber;
    @Enumerated(EnumType.STRING)
    private PassengerType passengerType;
    private int age;

    public PassengerEntity(UUID id, String name, String passportNumber, PassengerType passengerType, int age) {
        this.id = id;
        this.name = name;
        this.passportNumber = passportNumber;
        this.passengerType = passengerType;
        this.age = age;
    }
}
