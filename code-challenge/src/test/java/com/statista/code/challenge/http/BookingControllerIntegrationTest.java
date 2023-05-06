package com.statista.code.challenge.http;

import com.statista.code.challenge.business.Booking;
import com.statista.code.challenge.business.BookingService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.Currency;

import static java.util.Collections.singletonList;
import static org.hamcrest.Matchers.equalTo;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

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

        // When I POST it to /bookingservice/bookings
        mockMvc.perform(post("/bookingservice/bookings")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(booking))

                // Then the response is CREATED
                .andExpect(status().isCreated())

                // And then the response contains a location header
                .andExpect(header().string("location", equalTo("/bookingservice/bookings/findMe")));

        // And then the createBoooking usecase is executed
        verify(bookingService).create(any());
    }

    @Test
    void shouldPUT() throws Exception {
        when(bookingService.update(anyString(), any(Booking.class))).thenReturn(Booking.builder().booking_id("someId").build());

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

        // When I PUT it to /bookingservice/bookings/someId
        mockMvc.perform(put("/bookingservice/bookings/someId")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(booking))

                // Then the response is CREATED
                .andExpect(status().isOk());

        // And then the updateBoooking usecase is executed
        verify(bookingService).update(anyString(), any());
    }

    @Test
    void shouldGET() throws Exception {
        // Given a stored Booking with id x
        when(bookingService.search("x")).thenReturn(Booking.builder()
                .booking_id("x")
                .description("masterplan")
                .price(BigDecimal.valueOf(9.99))
                .currency(Currency.getInstance("EUR"))
                .email("'tis but a string")
                .subscription_start_date(Instant.now())
                .department("ministry of silly walks")
                .build());

        // When I GET from /bookingservice/bookings/x
        mockMvc.perform(
                        get("/bookingservice/bookings/x"))

                // Then the response is OK
                .andExpect(status().isOk())

                // And then the response contains a JSON representation of the booking
                .andExpect(jsonPath("$.email").value("'tis but a string"));
    }

    @Test
    void shouldGETbyDepartment() throws Exception {
        // Given a stored Booking with department y
        when(bookingService.searchByDepartment("y")).thenReturn(singletonList(Booking.builder()
                .booking_id("x")
                .email("nomail@statista.com")
                .department("y")
                .build()));

        // When I GET from /bookingservice/bookings/department/y
        mockMvc.perform(
                        get("/bookingservice/bookings/department/y"))

                // Then the response is OK
                .andExpect(status().isOk())

                // And then the response contains a JSON representation of the booking
                .andExpect(jsonPath("$[*].department").value("y"));
    }
}