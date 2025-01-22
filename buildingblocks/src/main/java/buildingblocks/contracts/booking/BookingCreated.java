package buildingblocks.contracts.booking;

import buildingblocks.core.event.IntegrationEvent;
import java.util.UUID;

public record BookingCreated(UUID Id) implements IntegrationEvent {
}

