package com.statista.code.challenge.business;

import com.statista.code.challenge.http.Booking;
import com.statista.code.challenge.persistence.BookingRepository;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.Currency;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

class BookingServiceTest {

    @Test
    void shouldSaveAndSendConfirmation() {
        NotificationService notificationService = mock(NotificationService.class);
        BookingRepository bookingRepository = mock(BookingRepository.class);
        when(bookingRepository.save(any())).thenReturn(
                Booking.builder()
                        .booking_id("generated by mock repo")
                        .description("masterplan")
                        .price(BigDecimal.valueOf(9.99))
                        .currency(Currency.getInstance("EUR"))
                        .email("'tis but a string")
                        .subscription_start_date(Instant.now())
                        .department("ministry of silly walks")
                        .build());
        BookingService bookingService = new BookingService(bookingRepository, notificationService);
        // Given the data for a Booking
        Booking booking = Booking.builder()
                .description("masterplan")
                .price(BigDecimal.valueOf(9.99))
                .currency(Currency.getInstance("EUR"))
                .email("'tis but a string")
                .subscription_start_date(Instant.now())
                .department("ministry of silly walks")
                .build();

        // When I create a Booking for it
        Booking createdBooking = bookingService.create(booking);

        // Then the Booking has an Id
        assertThat(createdBooking.booking_id()).isEqualTo("generated by mock repo");

        // And then a notification is sent
        verify(notificationService).send(createdBooking);
    }
}