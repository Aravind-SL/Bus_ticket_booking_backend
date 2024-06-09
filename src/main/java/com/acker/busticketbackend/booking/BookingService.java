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
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class BookingService {

    private final BookingRepository bookingRepository;
    private final SeatsRepository seatsRepository;

    private final BusService busService;


    public Booking createBooking(long busId, List<Integer> requestedSeatIds, LocalDateTime journeyDate) {
        LocalDateTime bookingDate = LocalDateTime.now();

        // Take the user detail from the security context
        User user = (User) SecurityContextHolder.getContext()
                .getAuthentication().getPrincipal();
        System.out.println("Here");

        Bus bus = busService.getBusById(busId);
        System.out.println(bus);

        // Get the bus object
//        Set<Seats> requestedSeats = new HashSet<>(seatsRepository.findAllById(requestedSeatIds));
//        System.out.println(requestedSeats);

        // TODO: Do a check for availability of the seats.

        // Create Booking
        Booking booking = Booking.builder()
                .user(user)
                .bus(bus)
                .bookingDate(bookingDate)
                .journeyDate(journeyDate)
                .status(BookingStatus.PENDING)
                .build();

        System.out.println(booking);

        // Set the seats to not available
//        requestedSeats.forEach(seat -> seat.setAvailable(false));
//
//        seatsRepository.saveAll(
//                requestedSeats
//        );

        return bookingRepository.save(booking);
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