package buildingblocks.rabbitmq;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "rabbitmq")
@Getter
@Setter
@ConditionalOnMissingBean
public class RabbitmqOptions {
    private String host;
    private int port;
    private String username;
    private String password;
    private String exchangeName;
    private String exchangeType;
}
