package com.statista.code.challenge.business;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Builder;
import org.springframework.lang.NonNull;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.Currency;

public record Booking(@JsonIgnore String booking_id, String description, BigDecimal price, Currency currency,
                      Instant subscription_start_date, @NonNull String email, String department) {

    @Builder
    public Booking {
    }
}
