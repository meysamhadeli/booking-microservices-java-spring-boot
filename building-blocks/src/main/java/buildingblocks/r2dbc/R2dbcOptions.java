package buildingblocks.r2dbc;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "spring.r2dbc")
@Getter
@Setter
@ConditionalOnMissingBean
public class R2dbcOptions {
    private String driver;
    private String host;
    private int port;
    private String database;
    private String username;
    private String password;
}

