package com.booking.microservices.java.spring.boot.flight;

import buildingblocks.core.event.EventDispatcherConfiguration;
import buildingblocks.flyway.FlywayConfiguration;
import buildingblocks.jpa.JpaConfiguration;
import buildingblocks.keycloak.KeycloakConfiguration;
import buildingblocks.logger.LoggerConfiguration;
import buildingblocks.mongo.MongoConfiguration;
import buildingblocks.otel.collector.OtelCollectorConfiguration;
import buildingblocks.outboxprocessor.PersistMessageProcessorConfiguration;
import buildingblocks.problemdetails.CustomProblemDetailsHandler;
import buildingblocks.rabbitmq.RabbitmqConfiguration;
import buildingblocks.swagger.SwaggerConfiguration;
import buildingblocks.threadpool.ThreadPoolConfiguration;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import({
  CustomProblemDetailsHandler.class,
  JpaConfiguration.class,
  MongoConfiguration.class,
  LoggerConfiguration.class,
  FlywayConfiguration.class,
  RabbitmqConfiguration.class,
  OtelCollectorConfiguration.class,
  SwaggerConfiguration.class,
  KeycloakConfiguration.class,
  ThreadPoolConfiguration.class,
  PersistMessageProcessorConfiguration.class,
  EventDispatcherConfiguration.class
})
public class FlightConfigurations {
}
