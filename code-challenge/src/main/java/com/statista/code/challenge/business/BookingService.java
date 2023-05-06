package com.statista.code.challenge.business;

import com.statista.code.challenge.persistence.BookingRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Currency;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class BookingService {
    private final BookingRepository bookingRepository;
    private final NotificationService notificationService;

    private final BusinessRegistry businessRegistry;

    public BookingService(BookingRepository bookingRepository, NotificationService notificationService, BusinessRegistry businessRegistry) {
        this.bookingRepository = bookingRepository;
        this.notificationService = notificationService;
        this.businessRegistry = businessRegistry;
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

    public List<Currency> searchCurrencies() {
        return bookingRepository.findAll().stream()
                .map(Booking::currency)
                .distinct()
                .collect(Collectors.toList());
    }

    public BigDecimal sumForCurrency(String currency) {
        return bookingRepository.findAll().stream()
                .filter(booking -> booking.currency().equals(Currency.getInstance(currency)))
                .map(Booking::price)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public String doBusiness(String booking_id) {
        Booking booking = bookingRepository.findById(booking_id);
        return businessRegistry.getDepartment(booking.department()).doBusiness(booking);
    }
}
