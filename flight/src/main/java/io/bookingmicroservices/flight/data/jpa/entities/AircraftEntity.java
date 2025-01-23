package io.bookingmicroservices.flight.data.jpa.entities;

import buildingblocks.core.model.BaseEntity;
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
@Table(name = "aircrafts")
@EntityListeners(AuditingEntityListener.class)
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class AircraftEntity extends BaseEntity<UUID> {
  private String name;
  private String model;
  private int manufacturingYear;

  public AircraftEntity(UUID id, String name, String model, int manufacturingYear, LocalDateTime createdAt, Long createdBy, LocalDateTime lastModified, Long lastModifiedBy, Long version, boolean isDeleted) {
    this.id = id;
    this.name = name;
    this.model = model;
    this.manufacturingYear = manufacturingYear;
    this.createdAt = createdAt;
    this.createdBy = createdBy;
    this.lastModified = lastModified;
    this.lastModifiedBy = lastModifiedBy;
    this.version = version;
    this.isDeleted = isDeleted;
  }

  public AircraftEntity(UUID id, String name, String model, int manufacturingYear) {
    this.id = id;
    this.name = name;
    this.model = model;
    this.manufacturingYear = manufacturingYear;
  }
}

