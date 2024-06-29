package com.acker.busticketbackend.booking;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/bookings")
@RequiredArgsConstructor
@CrossOrigin(value = "*", maxAge = 3600)
public class BookingController {

    private final BookingService bookingService;

    @PostMapping
    public ResponseEntity<BookingResponse> createBooking(@RequestBody AddBookingRequest addBookingRequest) {
        Booking booking = bookingService.createBooking(
                addBookingRequest.getBusId(),
                addBookingRequest.getNumberOfSeats(),
                addBookingRequest.getJourneyDate()
        );


        return new ResponseEntity<>(bookingToResponse(booking), HttpStatus.CREATED);
    }

    private BookingResponse bookingToResponse(Booking booking) {
        return BookingResponse.builder()
                .busId(booking.getBus().getBusId())
                .id(booking.getId())
                .bookingDate(booking.getBookingDate())
                .journeyDate(booking.getJourneyDate())
                .status(booking.getStatus())
                .userId(booking.getUser().getId())
                .seatBookings(booking.getSeatBookings()).build();
    }

    @GetMapping
    public ResponseEntity<List<BookingResponse>> getBookings() {

        return ResponseEntity.ok(bookingService.getAllBookings()
                .stream()
                .map(this::bookingToResponse)
                .toList()
        );
    }

    @GetMapping("/next/{busId}")
    public ResponseEntity<List<DailyBookingStatus>> getNextXDaysBooking(@PathVariable Long busId, @RequestParam Optional<Integer> days) {

        return ResponseEntity.ok(bookingService.getNextNDayBooking(busId, LocalDate.now(), days.orElse(7)));
    }
}
