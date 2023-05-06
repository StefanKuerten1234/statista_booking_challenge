package com.statista.code.challenge.business;

import com.statista.code.challenge.persistence.BookingRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

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

    public Booking search(String booking_id) {
        return bookingRepository.findById(booking_id);  // TODO: Handle null case
    }

    public Booking update(String booking_id, Booking booking) {
        return bookingRepository.save(
                new Booking(
                        booking_id,
                        booking.description(),
                        booking.price(),
                        booking.currency(),
                        booking.subscription_start_date(),
                        booking.email(),
                        booking.department()));
    }

    public List<Booking> searchByDepartment(String department) {
        return bookingRepository.findAll().stream()
                .filter(booking -> booking.department().equals(department))
                .collect(Collectors.toList());
    }
}
