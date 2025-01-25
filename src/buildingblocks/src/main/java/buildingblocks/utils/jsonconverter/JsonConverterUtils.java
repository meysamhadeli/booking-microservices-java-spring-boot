package buildingblocks.utils.jsonconverter;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConversionException;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.lang.Nullable;

import java.io.IOException;
import java.lang.reflect.Type;

public final class JsonConverterUtils {

  private JsonConverterUtils() {
    throw new AssertionError("Cannot instantiate utility class.");
  }

  private static final ObjectMapper objectMapper = new ObjectMapper()
    .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
    .registerModule(new JavaTimeModule());

  static {
    objectMapper.findAndRegisterModules();
  }

  public static String serializeObject(Object object) {
    try {
      return objectMapper.writeValueAsString(object);
    } catch (JsonProcessingException ex) {
      throw new RuntimeException("Serialization error", ex);
    }
  }

  public static <T> T deserialize(String json, Class<T> type) {
    try {
      return objectMapper.readValue(json, type);
    } catch (JsonProcessingException ex) {
      throw new RuntimeException("Deserialization error", ex);
    }
  }

  public static <T> T deserialize(byte[] bytes, Class<T> type) {
    try {
      return objectMapper.readValue(bytes, type);
    } catch (IOException ex) {
      throw new RuntimeException("Deserialization error", ex);
    }
  }
}
