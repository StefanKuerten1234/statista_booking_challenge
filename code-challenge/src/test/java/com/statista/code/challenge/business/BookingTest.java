package com.statista.code.challenge.business;

import com.statista.code.challenge.config.ObjectMapperConfig;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.Instant;
import java.util.Currency;

import static org.assertj.core.api.Assertions.assertThat;

class BookingTest {

    @Test
    void shouldDeserialize() throws Exception {
        // Given a JSON representation of a booking
        String booking = """
                {
                  "description": "Cool description!",
                  "price": 50.00,
                  "currency": "USD",
                  "subscription_start_date": 683124845000,
                  "email": "valid@email.ok",
                  "department": "cool department"
                }
                """;

        // When I deserialize it
        Booking actual = ObjectMapperConfig.getObjectMapper()
                .readValue(booking, Booking.class);

        // Then the result is a correct Booking
        assertThat(actual).isEqualTo(new Booking(
                null,
                "Cool description!",
                new BigDecimal(50).setScale(2, RoundingMode.HALF_UP),
                Currency.getInstance("USD"),
                Instant.ofEpochSecond(683124845000L),
                "valid@email.ok",
                "cool department"));
    }

    @Test
    void shouldSerialize() throws Exception {
        // Given a Booking
        Booking booking = new Booking(
                null,
                "Cool description!",
                new BigDecimal(50).setScale(2, RoundingMode.HALF_UP),
                Currency.getInstance("USD"),
                Instant.ofEpochSecond(683124845000L),
                "valid@email.ok",
                "cool department");

        // When I serialize it into JSON
        String actual = ObjectMapperConfig.getObjectMapper()
                .writeValueAsString(booking);

        // Then the result is a correct String
        assertThat(actual).isEqualToIgnoringWhitespace("""
                {"description": "Cool description!", "price": 50.00, "currency": "USD", "subscription_start_date": 683124845000, "email": "valid@email.ok", "department": "cool department"}
                """);
    }

    @Test
    void shouldPreserveIdentity() throws Exception {
        // Given a Booking
        Booking expected = new Booking(
                null,
                "Cool description!",
                new BigDecimal(50).setScale(2, RoundingMode.HALF_UP),
                Currency.getInstance("USD"),
                Instant.ofEpochSecond(683124845000L),
                "valid@email.ok",
                "cool department");

        // When I serialize it into JSON
        String jsonValue = ObjectMapperConfig.getObjectMapper().writeValueAsString(expected);

        // And then deserialize it again
        Booking actual = ObjectMapperConfig.getObjectMapper().readValue(jsonValue, Booking.class);

        // Then it should be the same Booking
        assertThat(actual).isEqualTo(expected);
    }
}