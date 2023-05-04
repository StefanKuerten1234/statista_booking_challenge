package com.statista.code.challenge.persistence;

import com.statista.code.challenge.business.Booking;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class InMemoryBookingRepository implements BookingRepository {

    private final ConcurrentHashMap<String, Booking> bookings = new ConcurrentHashMap<>();

    @Override
    public Booking save(Booking booking) {
        Booking createdBooking = new Booking(
                Optional.ofNullable(booking.booking_id()).orElse(UUID.randomUUID().toString()),
                booking.description(),
                booking.price(),
                booking.currency(),
                booking.subscription_start_date(),
                booking.email(),
                booking.department());
        bookings.put(createdBooking.booking_id(), createdBooking);
        return createdBooking;
    }

    @Override
    public Booking findById(String booking_id) {
        return bookings.get(booking_id);
    }
}
