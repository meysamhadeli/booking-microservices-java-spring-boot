package io.bookingmicroservices.flight.flights;

import org.springframework.context.annotation.Bean;
import org.springframework.web.context.request.RequestContextListener;

public class FilterBean {
  @Bean
  public RequestContextListener requestContextListener() {
    return new RequestContextListener();
  }

}
