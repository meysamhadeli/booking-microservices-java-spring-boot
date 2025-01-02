package buildingblocks.jpa;

import buildingblocks.logger.LoggerConfiguration;
import jakarta.persistence.EntityManagerFactory;
import org.flywaydb.core.Flyway;
import org.slf4j.Logger;
import org.springframework.boot.autoconfigure.flyway.FlywayMigrationStrategy;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import javax.sql.DataSource;
import java.util.Properties;

@Configuration
@Import(LoggerConfiguration.class)
public class JpaConfiguration {

    @Bean
    public JpaOptions jpaOptions() {
        return new JpaOptions();
    }

    @Bean
    public FlywayOptions flywayOptions() {
        return new FlywayOptions();
    }

    @Bean
    @ConditionalOnMissingBean
    public DataSource dataSource(JpaOptions jpaOptions) {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName(jpaOptions.getDatasourceDriverClassName());
        dataSource.setUrl(jpaOptions.getDatasourceUrl());
        dataSource.setUsername(jpaOptions.getDatasourceUsername());
        dataSource.setPassword(jpaOptions.getDatasourcePassword());
        return dataSource;
    }

    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory(DataSource dataSource, JpaOptions jpaOptions) {
        LocalContainerEntityManagerFactoryBean factoryBean = new LocalContainerEntityManagerFactoryBean();

        factoryBean.setDataSource(dataSource);
        factoryBean.setPackagesToScan(jpaOptions.getPackagesToScan());
        factoryBean.setJpaVendorAdapter(new HibernateJpaVendorAdapter());

        Properties jpaProperties = new Properties();
        jpaProperties.put("hibernate.hbm2ddl.auto", jpaOptions.getHibernateDdlAuto());
        jpaProperties.put("hibernate.column_ordering_strategy", jpaOptions.getHibernateColumnOrderingStrategy());
        jpaProperties.put("hibernate.show_sql", jpaOptions.getHibernateShowSql());
        jpaProperties.put("hibernate.format_sql", jpaOptions.getHibernateFormatSql());

        factoryBean.setJpaProperties(jpaProperties);
        return factoryBean;
    }

    @Bean
    public JpaTransactionManager transactionManager(EntityManagerFactory entityManagerFactory) {
        return new JpaTransactionManager(entityManagerFactory);
    }

    @Bean
    public FlywayMigrationStrategy flywayMigrationStrategy(FlywayOptions flywayOptions, Logger logger) {
        return flyway -> {
            if (!flywayOptions.isEnabled()) {
                System.out.println("Flyway migrations are disabled.");
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