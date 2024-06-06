package com.acker.busticketbackend.booking;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/bookings")
@RequiredArgsConstructor
public class BookingController {

    private final BookingService bookingService;

    @PostMapping
    public ResponseEntity<Booking> createBooking(@RequestBody AddBookingRequest addBookingRequest) {
        Booking booking = bookingService.createBooking(
                addBookingRequest.getBusNumber(),
                addBookingRequest.getSeatIds(),
                addBookingRequest.getJourneyDate()
        );
        return new ResponseEntity<>(booking, HttpStatus.CREATED);
    }
}
