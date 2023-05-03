package com.statista.code.challenge.persistence;

import com.statista.code.challenge.http.Booking;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.Instant;
import java.util.Currency;

import static org.assertj.core.api.Assertions.assertThat;

class InMemoryBookingRepositoryTest {

    @Test
    void shouldAssignId() {
        InMemoryBookingRepository inMemoryBookingRepository = new InMemoryBookingRepository();
        // Given a Booking without Id
        Booking booking = new Booking(
                null,
                "Cool description!",
                new BigDecimal(50).setScale(2, RoundingMode.HALF_UP),
                Currency.getInstance("USD"),
                Instant.ofEpochSecond(683124845000L),
                "valid@email.ok",
                "cool department");

        // When I save it
        Booking actual = inMemoryBookingRepository.save(booking);

        // Then the result is the same Booking with new Id assigned
        assertThat(actual.booking_id()).isNotEmpty();
        assertThat(actual).usingRecursiveComparison().ignoringFields("booking_id").isEqualTo(booking);
    }
}