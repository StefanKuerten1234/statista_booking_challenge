package com.statista.code.challenge.http;

import com.statista.code.challenge.business.Booking;
import com.statista.code.challenge.business.BookingService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;

@RestController
@RequestMapping("/bookingservice")
public class BookingController {

    private final BookingService bookingService;

    public BookingController(BookingService bookingService) {
        this.bookingService = bookingService;
    }

    @PostMapping("/booking")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Booking> createBooking(Booking booking) {
        return ResponseEntity.created(URI.create("/bookingservice/booking/" + bookingService.create(booking).booking_id())).build();

    }
//    @PutMapping("/booking/{transactionId}")
//    public ResponseEntity updateBooking() {
//        return ResponseEntity.ok().build();
//    }
//    @GetMapping("/booking/{bookingId}")
//    public ResponseEntity getBookingById() {
//        return ResponseEntity.ok().build();
//    }
//    @GetMapping("/booking/type/{type}")
//    public ResponseEntity getBookingsOfType() {
//        return ResponseEntity.ok().build();
//    }
}