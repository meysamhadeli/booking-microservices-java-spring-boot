package buildingblocks.jpa;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "flyway")
@Getter
@Setter
@ConditionalOnMissingBean
public class FlywayOptions {
    private boolean enabled;
    private String locations;
    private boolean baselineOnMigrate;
    private String baselineVersion;
    private boolean validateOnMigrate;
    private boolean cleanDisabled;
}