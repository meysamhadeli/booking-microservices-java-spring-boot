package buildingblocks.contracts.flight;

import buildingblocks.core.event.IntegrationEvent;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

public record FlightCreated(UUID Id) implements IntegrationEvent {
}
