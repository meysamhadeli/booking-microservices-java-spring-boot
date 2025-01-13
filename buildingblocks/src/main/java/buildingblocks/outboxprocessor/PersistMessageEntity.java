package buildingblocks.outboxprocessor;

import com.github.f4b6a3.uuid.UuidCreator;
import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "persist_messages")
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PersistMessageEntity {
    @Id
    private UUID id;

    @Column(nullable = false)
    private String dataType;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String data;

    @Column(nullable = false)
    private LocalDateTime created;

    @Column(nullable = false)
    private int retryCount;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private MessageStatus messageStatus;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private MessageDeliveryType deliveryType;

    @Version
    private Long version;

    // Public constructor for creating new messages
    public static PersistMessageEntity create(String dataType, String data, MessageDeliveryType deliveryType) {
        PersistMessageEntity message = new PersistMessageEntity();
        message.id = UuidCreator.getTimeOrderedEpoch();
        message.dataType = dataType;
        message.data = data;
        message.deliveryType = deliveryType;
        message.created = LocalDateTime.now();
        message.retryCount = 0;
        message.messageStatus = MessageStatus.InProgress;
        return message;
    }
}
