package com.acker.busticketbackend.booking;

import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.acker.busticketbackend.buses.Seats;

import java.time.LocalDateTime;
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
        return new ResponseEntity<>(booking, HttpStatus.CREATED);
    }

    @GetMapping("/buses/{busId}/booked-seats")
    public ResponseEntity<List<Seats>> getBookedSeats(@PathVariable long busId, @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime journeyDate) {
        List<Seats> bookedSeats = bookingService.getBookedSeats(busId, journeyDate);
        return new ResponseEntity<>(bookedSeats, HttpStatus.OK);
    }
}
