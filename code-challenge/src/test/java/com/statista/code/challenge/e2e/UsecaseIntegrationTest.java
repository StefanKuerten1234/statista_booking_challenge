package com.statista.code.challenge.e2e;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class UsecaseIntegrationTest {

    @Autowired
    WebTestClient webTestClient;

    @Test
    void shouldCreateAndFindBooking() {
        String bookingJson = """
                {
                  "description": "Cool description!",
                  "price": 50.00,
                  "currency": "USD",
                  "subscription_start_date": 683124845000,
                  "email": "valid@email.ok",
                  "department": "cool department"
                }
                """;

        String location = webTestClient.post()
                .uri("/bookingservice/booking")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(bookingJson)
                .exchange()
                .expectHeader()
                .value("location", locationHeader -> assertThat(locationHeader).startsWith("/bookingservice/booking/"))
                .returnResult(String.class)
                .getResponseHeaders()
                .getFirst("location");

        webTestClient.get()
                .uri(location)
                .exchange()
                .expectStatus().isOk()
                .expectBody(String.class)
                .value(json -> assertThat(json).isEqualToIgnoringWhitespace(bookingJson));
    }
}
