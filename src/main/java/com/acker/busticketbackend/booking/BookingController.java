package com.acker.busticketbackend.booking;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/bookings")
@RequiredArgsConstructor
public class BookingController {

    private final BookingService bookingService;

    @PostMapping
    public ResponseEntity<Booking> createBooking(@RequestBody AddBookingRequest addBookingRequest) {
        Booking booking = bookingService.createBooking(
                addBookingRequest.getBusId(),
                addBookingRequest.getSeatIds(),
                addBookingRequest.getJourneyDate()
        );

        System.out.println(booking);

        return new ResponseEntity<>(booking, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<SeatBooking>> getBookings() {

        return ResponseEntity.ok(bookingService.getBookedSeats());
    }
}
