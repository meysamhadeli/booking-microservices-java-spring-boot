package io.bookingmicroservices.flight.endtoendtest.flights.features;

import buildingblocks.testbase.EndToEndTestBase;
import buildingblocks.utils.jsonconverter.JsonConverterUtils;
import io.bookingmicroservices.flight.endtoendtest.fakes.CreateFlightCommandFake;
import io.bookingmicroservices.flight.flights.dtos.FlightDto;
import io.bookingmicroservices.flight.flights.features.createflight.CreateFlightCommand;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class CreateFlightTests extends EndToEndTestBase {

  @Test
  void should_create_flight_via_api_and_return_200() throws Exception {
    // Arrange
    CreateFlightCommand command = CreateFlightCommandFake.generate();

    // Act
    String responseContent = mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/flight")
        .contentType(MediaType.APPLICATION_JSON)
        .content(JsonConverterUtils.serializeObject(command)))
      .andExpect(MockMvcResultMatchers.status().isOk())
      .andReturn().getResponse().getContentAsString();

    FlightDto result = JsonConverterUtils.deserialize(responseContent, FlightDto.class);

    // Assert
    assertThat(result).isNotNull();
    assertThat(result.flightNumber()).isEqualTo("20H50");
  }
}
