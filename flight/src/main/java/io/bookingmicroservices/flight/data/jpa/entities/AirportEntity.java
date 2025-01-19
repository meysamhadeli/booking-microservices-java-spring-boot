package io.bookingmicroservices.flight.data.jpa.entities;

import buildingblocks.core.model.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import java.util.UUID;

@Entity
@Table(name = "airports")
@EntityListeners(AuditingEntityListener.class)
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class AirportEntity extends BaseEntity<UUID> {
  private String name;
  private String code;
  private String address;

  public AirportEntity(UUID id, String name, String code, String address) {
    this.id = id;
    this.name = name;
    this.code = code;
    this.address = address;
  }
}
