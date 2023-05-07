package com.statista.code.challenge.http;

import com.statista.code.challenge.business.Booking;
import com.statista.code.challenge.business.BookingService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
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
    @Operation(summary = "Create a booking")
    public ResponseEntity<Booking> createBooking(@RequestBody Booking booking) {
        return ResponseEntity.created(URI.create("/bookingservice/bookings/" + bookingService.create(booking).booking_id())).build();
    }

    @PutMapping("/bookings/{bookingId}")
    @Operation(summary = "Update a booking")
    public ResponseEntity<Void> updateBooking(@RequestBody Booking booking, @PathVariable String bookingId) {
        bookingService.update(bookingId, booking);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/bookings/{bookingId}")
    @Operation(summary = "Get a booking")
    public ResponseEntity<Booking> getBookingById(@PathVariable String bookingId) {
        return ResponseEntity.ok(bookingService.search(bookingId));
    }

    @GetMapping("/bookings/department/{department}")
    @Operation(summary = "Get bookings for a department")
    public ResponseEntity<List<Booking>> getBookingByDepartment(@PathVariable String department) {
        return ResponseEntity.ok(bookingService.searchByDepartment(department));
    }

    @GetMapping("/bookings/currencies")
    @Operation(summary = "Get currencies used in either Booking")
    public ResponseEntity<List<Currency>> getCurrencies() {
        return ResponseEntity.ok(bookingService.searchCurrencies());
    }

    @GetMapping("/sum/{currency}")
    @Operation(summary = "Get sum of all bookings for a currency")
    public ResponseEntity<BigDecimal> getSumForCurrency(@PathVariable String currency) {
        return ResponseEntity.ok(bookingService.sumForCurrency(currency));
    }

    @GetMapping("/bookings/dobusiness/{booking_id}")
    @Operation(summary = "Do business according to the specification of the owning business department")
    public ResponseEntity<String> getBusinessResult(@PathVariable String booking_id) {
        return ResponseEntity.ok(bookingService.doBusiness(booking_id));
    }
}