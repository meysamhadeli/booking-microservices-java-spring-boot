package buildingblocks.flyway;

import org.flywaydb.core.Flyway;
import org.slf4j.Logger;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingClass;
import org.springframework.boot.autoconfigure.flyway.FlywayMigrationStrategy;
import org.springframework.boot.autoconfigure.flyway.FlywayProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import(FlywayProperties.class)
public class FlywayConfiguration {

    private final FlywayProperties flywayProperties;

    public FlywayConfiguration(FlywayProperties flywayProperties) {
        this.flywayProperties = flywayProperties;
    }

    @Bean
    @ConditionalOnMissingClass
    public FlywayMigrationStrategy flywayMigrationStrategy(Logger logger) {
        return flyway -> {
            if (!flywayProperties.isEnabled()) {
                logger.info("Flyway migrations are disabled.");
                return;
            }

            Flyway configuredFlyway = Flyway.configure()
                    .dataSource(flyway.getConfiguration().getDataSource())
                    .locations(flywayProperties.getLocations().toArray(new String[0]))
                    .baselineOnMigrate(flywayProperties.isBaselineOnMigrate())
                    .baselineVersion(flywayProperties.getBaselineVersion())
                    .validateOnMigrate(flywayProperties.isValidateOnMigrate())
                    .cleanDisabled(flywayProperties.isCleanDisabled())
                    .load();

            logger.info("Starting Flyway migration...");
            try {
                configuredFlyway.migrate();
                logger.info("Flyway migration completed successfully!");
            } catch (Exception ex) {
                logger.error("Flyway migration failed: {}", ex.getMessage(), ex);
                throw ex;
            }
        };
    }
}
