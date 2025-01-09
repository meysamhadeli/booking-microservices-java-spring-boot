package com.booking.microservices.java.spring.boot.flight;

import buildingblocks.flyway.FlywayConfiguration;
import buildingblocks.jpa.JpaConfiguration;
import buildingblocks.jpa.JpaTransactionCoordinator;
import buildingblocks.keycloak.KeycloakConfiguration;
import buildingblocks.otel.collector.OtelCollectorConfiguration;
import buildingblocks.rabbitmq.RabbitmqConfiguration;
import buildingblocks.swagger.SwaggerConfiguration;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import({
  JpaConfiguration.class,
  FlywayConfiguration.class,
  RabbitmqConfiguration.class,
  OtelCollectorConfiguration.class,
  SwaggerConfiguration.class,
  JpaTransactionCoordinator.class,
  KeycloakConfiguration.class
})
public class FlightConfigurations {

}
