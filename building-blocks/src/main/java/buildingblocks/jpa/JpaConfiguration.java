package buildingblocks.jpa;

import buildingblocks.core.event.EventMapper;
import buildingblocks.outboxprocessor.PersistMessageProcessor;
import jakarta.persistence.EntityManagerFactory;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingClass;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;
import java.util.Properties;

@Configuration
@EnableJpaAuditing
public class JpaConfiguration {

    @Bean
    public JpaOptions jpaOptions() {
        return new JpaOptions();
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
        factoryBean.setPackagesToScan(jpaOptions.getPackagesToScan(), "buildingblocks");
        factoryBean.setJpaVendorAdapter(new HibernateJpaVendorAdapter());

        Properties jpaProperties = new Properties();
        jpaProperties.put("hibernate.hbm2ddl.auto", jpaOptions.getHibernateDdlAuto());
        jpaProperties.put("hibernate.column_ordering_strategy", jpaOptions.getHibernateColumnOrderingStrategy());
        jpaProperties.put("hibernate.show_sql", jpaOptions.getHibernateShowSql());
        jpaProperties.put("hibernate.format_sql", jpaOptions.getHibernateFormatSql());
        jpaProperties.put("hibernate.physical_naming_strategy", jpaOptions.getHibernatePhysicalNamingStrategy());

        factoryBean.setJpaProperties(jpaProperties);
        return factoryBean;
    }

    @Bean
    public JpaTransactionManager transactionManager(EntityManagerFactory entityManagerFactory) {
        return new JpaTransactionManager(entityManagerFactory);
    }

    @Bean
    public AuditorAware<Long> auditorAware() {
        return new JpaAuditorAwareImpl();
    }

    @Bean
    public HibernateGlobalFilter hibernateFilterConfigurer(EntityManagerFactory entityManagerFactory) {
        SessionFactory sessionFactory = entityManagerFactory.unwrap(SessionFactory.class);
        return new HibernateGlobalFilter(sessionFactory);
    }

    @Bean
    @ConditionalOnMissingClass
    public JpaTransactionCoordinator transactionCoordinator(
            PlatformTransactionManager platformTransactionManager,
            PersistMessageProcessor persistMessageProcessor,
            Logger logger,
            EventMapper eventMapper) {
        return new JpaTransactionCoordinator(platformTransactionManager, persistMessageProcessor, logger, eventMapper);
    }
}