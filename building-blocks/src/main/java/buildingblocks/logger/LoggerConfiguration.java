package buildingblocks.logger;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class LoggerConfiguration {

    @Value("${logging.name:default-logger}")
    private String defaultLoggerName;

    @Bean
    public Logger defaultLogger() {
        return LoggerFactory.getLogger(defaultLoggerName);
    }
}