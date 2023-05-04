package com.statista.code.challenge.http;

import com.statista.code.challenge.business.Booking;
import com.statista.code.challenge.business.BookingService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequestMapping("/bookingservice")
public class BookingController {

    private final BookingService bookingService;

    public BookingController(BookingService bookingService) {
        this.bookingService = bookingService;
    }

    @PostMapping("/booking")
    public ResponseEntity<Booking> createBooking(@RequestBody Booking booking) {
        return ResponseEntity.created(URI.create("/bookingservice/booking/" + bookingService.create(booking).booking_id())).build();
    }

    @PutMapping("/booking/{bookingId}")
    public ResponseEntity<Void> updateBooking(@RequestBody Booking booking, @PathVariable String bookingId) {
        bookingService.update(bookingId, booking);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/booking/{bookingId}")
    public ResponseEntity<Booking> getBookingById(@PathVariable String bookingId) {
        return ResponseEntity.ok(bookingService.search(bookingId));
    }
//    @GetMapping("/booking/type/{type}")
//    public ResponseEntity getBookingsOfType() {
//        return ResponseEntity.ok().build();
//    }
}