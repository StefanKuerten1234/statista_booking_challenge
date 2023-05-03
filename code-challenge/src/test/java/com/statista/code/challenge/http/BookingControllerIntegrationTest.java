package com.statista.code.challenge.http;

import com.statista.code.challenge.business.BookingService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(BookingController.class)
class BookingControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BookingService bookingService;

    @Test
    void shouldPOST() throws Exception {
        when(bookingService.create(any(Booking.class))).thenReturn(Booking.builder().build());

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
                .andExpect(status().isCreated());
    }
}