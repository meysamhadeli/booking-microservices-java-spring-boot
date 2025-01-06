package buildingblocks.core.event;

import com.github.f4b6a3.uuid.UuidCreator;

import java.time.LocalDateTime;
import java.util.UUID;

public interface IEvent {
    default UUID eventId() {
        return UuidCreator.getTimeOrderedEpoch();
    }

    default LocalDateTime occurredOn() {
        return LocalDateTime.now();
    }
}
