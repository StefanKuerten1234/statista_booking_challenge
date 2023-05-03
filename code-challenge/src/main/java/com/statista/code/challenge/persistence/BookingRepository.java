package com.statista.code.challenge.persistence;

import com.statista.code.challenge.business.Booking;

public interface BookingRepository {

    Booking save(Booking booking);

    Booking findById(String y);
}
