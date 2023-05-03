package com.statista.code.challenge.persistence;

import com.statista.code.challenge.business.Booking;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public class InMemoryBookingRepository implements BookingRepository {

    @Override
    public Booking save(Booking booking) {

        return new Booking(
                UUID.randomUUID().toString(),
                booking.description(),
                booking.price(),
                booking.currency(),
                booking.subscription_start_date(),
                booking.email(),
                booking.department()
        );
    }
}
