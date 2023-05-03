package com.statista.code.challenge.business;

import com.statista.code.challenge.http.Booking;

public interface NotificationService {
    void send(Booking booking);
}
