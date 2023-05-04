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
    void shouldCreateUpdateAndFindBooking() {
        String initialJson = """
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
                .bodyValue(initialJson)
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
                .value(json -> assertThat(json).isEqualToIgnoringWhitespace(initialJson));

        String modifiedJson = """
                {
                  "description": "Another Cool description!",
                  "price": 51.00,
                  "currency": "INR",
                  "subscription_start_date": 683124845001,
                  "email": "alsovalid@email.ok",
                  "department": "very cool department"
                }
                """;

        webTestClient.put()
                .uri(location)
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(modifiedJson)
                .exchange()
                .expectStatus().isOk();

        webTestClient.get()
                .uri(location)
                .exchange()
                .expectStatus().isOk()
                .expectBody(String.class)
                .value(json -> assertThat(json).isEqualToIgnoringWhitespace(modifiedJson));
    }
}
