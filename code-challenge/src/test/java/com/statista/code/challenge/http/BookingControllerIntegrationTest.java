package com.statista.code.challenge.http;

import com.statista.code.challenge.business.Booking;
import com.statista.code.challenge.business.BookingService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.equalTo;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(BookingController.class)
class BookingControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BookingService bookingService;

    @Test
    void shouldPOST() throws Exception {
        when(bookingService.create(any(Booking.class))).thenReturn(Booking.builder().booking_id("findMe").build());

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

        // When I POST it to /bookingservice/booking
        mockMvc.perform(post("/bookingservice/booking").content(booking))

                // Then the response is CREATED
                .andExpect(status().isCreated())

                // And then the response contains a location header
                .andExpect(header().string("location", equalTo("/bookingservice/booking/findMe")));

        // And then the createBoooking usecase is executed
        verify(bookingService).create(any());
    }
}