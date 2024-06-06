package com.acker.busticketbackend.booking;

import com.acker.busticketbackend.auth.user.User;
import com.acker.busticketbackend.buses.Bus;
import com.acker.busticketbackend.buses.BusService;
import com.acker.busticketbackend.buses.Seats;
import com.acker.busticketbackend.buses.SeatsRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BookingService {

    private final BookingRepository bookingRepository;
    private final SeatsRepository seatsRepository;

    private final BusService busService;


    @Transactional
    public Booking createBooking(long busNumber, List<Integer> requestedSeatIds, LocalDateTime journeyDate) {

        String transactionId = UUID.randomUUID().toString();
        LocalDateTime bookingDate = LocalDateTime.now();

        // Take the user detail from the security context
        User user = (User) SecurityContextHolder.getContext()
                .getAuthentication().getPrincipal();

        // Get the bus object
        Bus bus = busService.getBusById(busNumber);

        HashSet<Seats> requestedSeats = bus.getSeats().stream()
                .filter(seat -> requestedSeatIds.contains(seat.getId()))
                .collect(Collectors.toCollection(HashSet::new));


        // Validate SeatIds.
        HashSet<Seats> availableSeats = bus.getSeats()
                .stream()
                .filter(Seats::isAvailable)
                .collect(Collectors.toCollection(HashSet::new));

        // Make sure whether all the requested seats are in available seats.
        if (!availableSeats.containsAll(requestedSeats)) {
            throw new RuntimeException("Requested already reserved seats: " + requestedSeats.removeAll(availableSeats));
        }

        // Create Booking
        Booking booking = Booking.builder()
                .id(transactionId)
                .user(user)
                .bus(bus)
                .seats(requestedSeats.stream().toList())
                .bookingDate(bookingDate)
                .journeyDate(journeyDate)
                .status(BookingStatus.PENDING)
                .build();

        // Set the seats to not available
        requestedSeats.forEach(seat -> seat.setAvailable(false));

        seatsRepository.saveAll(
                requestedSeats
        );

        return bookingRepository.saveAndFlush(booking);
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