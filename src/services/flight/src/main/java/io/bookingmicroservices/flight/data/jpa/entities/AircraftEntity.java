package io.bookingmicroservices.flight.data.jpa.entities;

import buildingblocks.core.model.BaseEntity;
import io.bookingmicroservices.flight.aircrafts.valueobjects.ManufacturingYear;
import io.bookingmicroservices.flight.aircrafts.valueobjects.Model;
import io.bookingmicroservices.flight.aircrafts.valueobjects.Name;
import jakarta.persistence.*;
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
  @Embedded
  private Name name;
  @Embedded
  private Model model;
  @Embedded
  private ManufacturingYear manufacturingYear;

  public AircraftEntity(UUID id, Name name, Model model, ManufacturingYear manufacturingYear, LocalDateTime createdAt, Long createdBy, LocalDateTime lastModified, Long lastModifiedBy, Long version, boolean isDeleted) {
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

  public AircraftEntity(UUID id, Name name, Model model, ManufacturingYear manufacturingYear) {
    this.id = id;
    this.name = name;
    this.model = model;
    this.manufacturingYear = manufacturingYear;
  }
}

