package buildingblocks.core.event;

import com.github.f4b6a3.uuid.UuidCreator;

import java.time.LocalDateTime;
import java.util.UUID;

public interface IEvent {
    default UUID getEventId() {
        return UuidCreator.getTimeOrderedEpoch();
    }

    default LocalDateTime getOccurredOn() {
        return LocalDateTime.now();
    }

    default String getEventType() {
        return this.getClass().getTypeName();
    }
}
