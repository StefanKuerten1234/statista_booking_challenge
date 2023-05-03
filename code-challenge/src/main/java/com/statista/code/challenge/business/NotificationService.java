package com.statista.code.challenge.business;

@FunctionalInterface
public interface NotificationService {
    void send(Booking booking);
}
