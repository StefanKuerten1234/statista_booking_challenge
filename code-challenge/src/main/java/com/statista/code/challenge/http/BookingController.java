package com.statista.code.challenge.http;

import com.statista.code.challenge.business.Booking;
import com.statista.code.challenge.business.BookingService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.Currency;
import java.util.List;

@RestController
@RequestMapping("/bookingservice")
public class BookingController {

    private final BookingService bookingService;

    public BookingController(BookingService bookingService) {
        this.bookingService = bookingService;
    }

    @PostMapping("/bookings")
    public ResponseEntity<Booking> createBooking(@RequestBody Booking booking) {
        return ResponseEntity.created(URI.create("/bookingservice/bookings/" + bookingService.create(booking).booking_id())).build();
    }

    @PutMapping("/bookings/{bookingId}")
    public ResponseEntity<Void> updateBooking(@RequestBody Booking booking, @PathVariable String bookingId) {
        bookingService.update(bookingId, booking);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/bookings/{bookingId}")
    public ResponseEntity<Booking> getBookingById(@PathVariable String bookingId) {
        return ResponseEntity.ok(bookingService.search(bookingId));
    }

    @GetMapping("/bookings/department/{department}")
    public ResponseEntity<List<Booking>> getBookingByDepartment(@PathVariable String department) {
        return ResponseEntity.ok(bookingService.searchByDepartment(department));
    }

    @GetMapping("/bookings/currencies")
    public ResponseEntity<List<Currency>> getBookingByDepartment() {
        return ResponseEntity.ok(bookingService.searchCurrencies());
    }

}