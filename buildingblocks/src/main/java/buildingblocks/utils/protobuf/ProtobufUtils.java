package buildingblocks.utils.protobuf;

import com.google.protobuf.Timestamp;
import com.google.protobuf.util.Timestamps;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

public final class ProtobufUtils {

    public static Timestamp toProtobufTimestamp(LocalDateTime localDateTime) {
        return Timestamps.fromMillis(localDateTime.toInstant(ZoneOffset.UTC).toEpochMilli());
    }

    public static LocalDateTime toLocalDateTime(Timestamp timestamp) {
        Instant instant = Instant.ofEpochSecond(timestamp.getSeconds(), timestamp.getNanos());
        return LocalDateTime.ofInstant(instant, ZoneOffset.UTC);
    }
}
