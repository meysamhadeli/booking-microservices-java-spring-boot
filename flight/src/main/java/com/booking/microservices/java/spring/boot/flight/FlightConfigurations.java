package com.booking.microservices.java.spring.boot.flight;

import buildingblocks.flyway.FlywayConfiguration;
import buildingblocks.jpa.JpaConfiguration;
import buildingblocks.keycloak.KeycloakConfiguration;
import buildingblocks.logger.LoggerConfiguration;
import buildingblocks.otel.collector.OtelCollectorConfiguration;
import buildingblocks.outboxprocessor.PersistMessageProcessorConfiguration;
import buildingblocks.rabbitmq.RabbitmqConfiguration;
import buildingblocks.swagger.SwaggerConfiguration;
import buildingblocks.threadpool.ThreadPoolConfiguration;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import({
  JpaConfiguration.class,
  LoggerConfiguration.class,
  FlywayConfiguration.class,
  RabbitmqConfiguration.class,
  OtelCollectorConfiguration.class,
  SwaggerConfiguration.class,
  KeycloakConfiguration.class,
  ThreadPoolConfiguration.class,
  PersistMessageProcessorConfiguration.class
})
public class FlightConfigurations {
}
