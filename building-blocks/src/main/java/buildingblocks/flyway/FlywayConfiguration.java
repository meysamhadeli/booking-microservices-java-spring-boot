package buildingblocks.flyway;

import buildingblocks.logger.LoggerConfiguration;
import org.flywaydb.core.Flyway;
import org.slf4j.Logger;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingClass;
import org.springframework.boot.autoconfigure.flyway.FlywayMigrationStrategy;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
public class FlywayConfiguration {
    @Bean
    public FlywayOptions flywayOptions() {
        return new FlywayOptions();
    }

    @Bean
    @ConditionalOnMissingClass
    public FlywayMigrationStrategy flywayMigrationStrategy(FlywayOptions flywayOptions, Logger logger) {
        return flyway -> {
            if (!flywayOptions.isEnabled()) {
                logger.info("Flyway migrations are disabled.");
                return;
            }

            Flyway configuredFlyway = Flyway.configure()
                    .dataSource(flyway.getConfiguration().getDataSource())
                    .locations(flywayOptions.getLocations())
                    .baselineOnMigrate(flywayOptions.isBaselineOnMigrate())
                    .baselineVersion(flywayOptions.getBaselineVersion())
                    .validateOnMigrate(flywayOptions.isValidateOnMigrate())
                    .cleanDisabled(flywayOptions.isCleanDisabled())
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
