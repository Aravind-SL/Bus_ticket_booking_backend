package com.acker.busticketbackend.booking;

import com.acker.busticketbackend.users.UserResponse;
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
        var user = UserResponse.builder()
                .username(booking.getUser().getUsername())
                .firstName(booking.getUser().getFirstName())
                .lastName(booking.getUser().getLastName())
                .build();
        return BookingResponse.builder()
                .busId(booking.getBus().getBusId())
                .id(booking.getId())
                .bookingDate(booking.getBookingDate())
                .journeyDate(booking.getJourneyDate())
                .status(booking.getStatus())
                .user(user)
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

    @GetMapping("/seatAvailable/{busId}")
    public ResponseEntity<DailyBookingStatus> getAvailableSeats(@PathVariable Long busId, @RequestBody Optional<LocalDate> day) {
        return ResponseEntity.ok(bookingService.getAvailableSeats(busId, day.orElse(LocalDate.now().plusDays(1))));
    }


    @PostMapping("/approve")
    public ResponseEntity<BookingProcessingResponse> processBooking(@RequestBody ProcessBookingRequest request) {
        return ResponseEntity.ok(bookingService.completeBooking(
                request.getBookingId(),
                request.isApproved(),
                request.getMessage()
        ));
    }
}
