package buildingblocks.core.event;

import com.github.f4b6a3.uuid.UuidCreator;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.UUID;
import java.util.WeakHashMap;

public interface IEvent {
    // A WeakHashMap to store the eventId for each instance
    Map<IEvent, UUID> EVENT_IDS = new WeakHashMap<>();
    Map<IEvent, LocalDateTime> EVENT_OCCURRED = new WeakHashMap<>();


    // Generate or retrieve the unique eventId for the instance
    default UUID getEventId() {
        return EVENT_IDS.computeIfAbsent(this, _ -> UuidCreator.getTimeOrderedEpoch());
    }

    // Retrieve the unique occurredOn for the instance
    default LocalDateTime getOccurredOn() {
        return EVENT_OCCURRED.computeIfAbsent(this, _ ->  LocalDateTime.now());
    }

    default String getEventType() {
        return this.getClass().getTypeName();
    }
}
