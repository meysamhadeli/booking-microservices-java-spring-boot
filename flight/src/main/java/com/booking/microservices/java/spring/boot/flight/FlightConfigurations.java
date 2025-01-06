package com.booking.microservices.java.spring.boot.flight;

import buildingblocks.axon.AxonConfiguration;
import buildingblocks.jpa.JpaConfiguration;
import buildingblocks.jpa.TransactionCoordinator;
import buildingblocks.otel.collector.OtelCollectorConfiguration;
import buildingblocks.rabbitmq.RabbitmqConfiguration;
import buildingblocks.swagger.SwaggerConfiguration;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import({
  JpaConfiguration.class,
  RabbitmqConfiguration.class,
  OtelCollectorConfiguration.class,
  SwaggerConfiguration.class,
  AxonConfiguration.class,
  TransactionCoordinator.class})
public class FlightConfigurations {
}
