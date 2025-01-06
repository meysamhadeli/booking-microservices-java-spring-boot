package buildingblocks.contracts;

import buildingblocks.core.event.IntegrationEvent;
import java.util.UUID;

public class FlightContracts {
    public record FlightCreated(UUID Id) implements IntegrationEvent {
    }

    public record FlightUpdated(UUID Id) implements IntegrationEvent {
    }

    public record FlightDeleted(UUID Id) implements IntegrationEvent {
    }
}
