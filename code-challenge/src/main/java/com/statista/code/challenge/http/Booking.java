package com.statista.code.challenge.http;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Builder;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.Currency;

public record Booking(@JsonIgnore String booking_id, String description, BigDecimal price, Currency currency,
                      Instant subscription_start_date, String email, String department) {

    @Builder
    public Booking {
    }
}
