package com.statista.code.challenge.business;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class BusinessRegistryTest {

    @Test
    void shouldReturnDefaultBusinessDepartment() {
        BusinessRegistry businessRegistry = new BusinessRegistry();

        // Given a Booking with unknown department
        Booking booking = Booking.builder()
                .department("unknown").build();

        // When I do the business for it
        String actual = businessRegistry.getDepartment(booking.department()).doBusiness(booking);

        // Then the default business operation is conducted
        assertThat(actual).isEqualTo("Nothing has been done since the department unknown is unknown");
    }

    @Test
    void shouldReturnSalesDepartment() {
        BusinessRegistry businessRegistry = new BusinessRegistry();

        // Given a Booking with sales department
        Booking booking = Booking.builder()
                .department("sales").build();

        // When I do the business for it
        String actual = businessRegistry.getDepartment(booking.department()).doBusiness(booking);

        // Then the sales department business operation is conducted
        assertThat(actual).isEqualTo("Voucher code for your next booking is HIRING23");
    }
}