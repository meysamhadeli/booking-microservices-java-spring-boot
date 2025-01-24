package io.bookingmicroservices.flight.data.jpa.entities;

import buildingblocks.core.model.BaseEntity;
import io.bookingmicroservices.flight.airports.valueobjects.Address;
import io.bookingmicroservices.flight.airports.valueobjects.Code;
import io.bookingmicroservices.flight.airports.valueobjects.Name;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "airports")
@EntityListeners(AuditingEntityListener.class)
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class AirportEntity extends BaseEntity<UUID> {
  @Embedded
  private Name name;
  @Embedded
  private Code code;
  @Embedded
  private Address address;

  public AirportEntity(UUID id, Name name, Code code, Address address, LocalDateTime createdAt, Long createdBy, LocalDateTime lastModified, Long lastModifiedBy, Long version, boolean isDeleted) {
    this.id = id;
    this.name = name;
    this.code = code;
    this.address = address;
    this.createdAt = createdAt;
    this.createdBy = createdBy;
    this.lastModified = lastModified;
    this.lastModifiedBy = lastModifiedBy;
    this.version = version;
    this.isDeleted = isDeleted;
  }

  public AirportEntity(UUID id, Name name, Code code, Address address) {
    this.id = id;
    this.name = name;
    this.code = code;
    this.address = address;
  }
}
