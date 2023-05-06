package com.statista.code.challenge.persistence;

import com.statista.code.challenge.business.Booking;

import java.util.List;

public interface BookingRepository {

    Booking save(Booking booking);

    Booking findById(String y);

    List<Booking> findAll();
}
