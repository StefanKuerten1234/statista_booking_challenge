package com.statista.code.challenge.business;

import com.statista.code.challenge.persistence.BookingRepository;
import org.springframework.stereotype.Service;

@Service
public class BookingService {
    private final BookingRepository bookingRepository;
    private final NotificationService notificationService;

    public BookingService(BookingRepository bookingRepository, NotificationService notificationService) {
        this.bookingRepository = bookingRepository;
        this.notificationService = notificationService;
    }

    public Booking create(Booking booking) {
        Booking savedBooking = bookingRepository.save(booking);
        notificationService.send(savedBooking);
        return savedBooking;
    }
}
