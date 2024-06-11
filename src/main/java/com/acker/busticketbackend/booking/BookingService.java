package com.acker.busticketbackend.booking;

import com.acker.busticketbackend.auth.user.User;
import com.acker.busticketbackend.buses.Bus;
import com.acker.busticketbackend.buses.BusService;
import com.acker.busticketbackend.buses.Seats;
import com.acker.busticketbackend.buses.SeatsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class BookingService {

    private final BookingRepository bookingRepository;
    private final SeatsRepository seatsRepository;

    private final BusService busService;


    public Booking createBooking(long busId, List<Integer> requestedSeatIds, LocalDateTime journeyDate) {
        LocalDateTime bookingDate = LocalDateTime.now();

        // Take the user detail from the security context
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        // Get the bus object
        Bus bus = busService.getBusById(busId);

        // Check for availability of the seats on the specified journey date
        Set<Seats> requestedSeats = new HashSet<>();
        for (Integer seatId : requestedSeatIds) {
            Seats seat = seatsRepository.findById(seatId).orElseThrow(() -> new RuntimeException("Seat not found: " + seatId));
            if (isSeatAvailable(seat, journeyDate)) {
                requestedSeats.add(seat);
            } else {
                throw new RuntimeException("Seat not available: " + seatId);
            }
        }

        // Create Booking
        Booking booking = Booking.builder()
                .user(user)
                .bus(bus)
                .seats(requestedSeats)
                .bookingDate(bookingDate)
                .journeyDate(journeyDate)
                .status(BookingStatus.PENDING)
                .build();

        // Save the booking and update seat availability
        requestedSeats.forEach(seat -> {
            seat.setAvailable(false);
            seatsRepository.save(seat);
        });

        return bookingRepository.save(booking);
    }

    private boolean isSeatAvailable(Seats seat, LocalDateTime journeyDate) {
        return bookingRepository.findByBusAndSeatsAndJourneyDate(seat.getBus(), seat, journeyDate).isEmpty();
    }


    /**
     * Returns the booking with its final status.
     * Should be used with payment service.
     *
     * @Param bookingId the id of the booking
     * @Param status the final status of the booking (Fail or Complete)
     * @Return the final booking object
     */
    public Booking completeBooking(String bookingId, BookingStatus status) {
        Booking booking = bookingRepository.findById(bookingId)
                .orElseThrow(() -> new RuntimeException("Booking Not exists: " + bookingId));

        if (status == BookingStatus.PENDING) {
            throw new RuntimeException("Give an Invalid State to set" + status);
        }

        booking.setStatus(status);
        return bookingRepository.save(booking);
    }

}