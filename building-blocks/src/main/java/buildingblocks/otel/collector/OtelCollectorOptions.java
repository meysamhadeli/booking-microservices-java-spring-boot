package buildingblocks.otel.collector;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "spring.otel.collector")
@Getter
@Setter
@ConditionalOnMissingBean
public class OtelCollectorOptions {
    private String endpoint;
    private String serviceName;
    private String serviceVersion;
}
