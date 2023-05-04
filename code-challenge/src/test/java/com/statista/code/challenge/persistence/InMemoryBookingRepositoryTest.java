package com.statista.code.challenge.persistence;

import com.statista.code.challenge.business.Booking;
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
                BigDecimal.valueOf(50).setScale(2, RoundingMode.HALF_UP),
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

    @Test
    void shouldPreserveId() {
        InMemoryBookingRepository inMemoryBookingRepository = new InMemoryBookingRepository();
        // Given a Booking with Id
        Booking booking = Booking.builder().booking_id("already assigned").build();

        // When I save it
        Booking actual = inMemoryBookingRepository.save(booking);

        // Then the result has the same Id
        assertThat(actual.booking_id()).isEqualTo(booking.booking_id());

    }

    @Test
    void shouldRetrieveSavedBooking() {
        InMemoryBookingRepository inMemoryBookingRepository = new InMemoryBookingRepository();

        // Given a Booking
        Booking booking = Booking.builder()
                .description("Cool description!")
                .price(BigDecimal.valueOf(50).setScale(2, RoundingMode.HALF_UP))
                .currency(Currency.getInstance("USD"))
                .subscription_start_date(Instant.ofEpochSecond(683124845000L))
                .email("valid@email.ok")
                .department("cool department")
                .build();

        // When I save it
        Booking saved = inMemoryBookingRepository.save(booking);

        // And then I retrieve it
        Booking found = inMemoryBookingRepository.findById(saved.booking_id());

        // Then the result is the same Booking
        assertThat(found).isEqualTo(saved);
    }
}