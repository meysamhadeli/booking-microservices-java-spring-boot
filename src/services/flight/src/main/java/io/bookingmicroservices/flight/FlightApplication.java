package io.bookingmicroservices.flight;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
public class FlightApplication {
	public static void main(String[] args) {
		SpringApplication.run(FlightApplication.class, args);
	}
}
