package com.acker.busticketbackend.booking;

import com.acker.busticketbackend.seats.Seats;
import com.acker.busticketbackend.seats.SeatsRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class BookingService {

    private final BookingRepository bookingRepository;
    private final SeatsRepository seatsRepository;

    @Transactional
    public Booking createBooking(int userId, int busNumber, List<Integer> seatIds, LocalDateTime journeyDate) {

        String transactionId = UUID.randomUUID().toString();
        LocalDateTime bookingDate = LocalDateTime.now();

        for (Integer seatId : seatIds) {
            Seats seat = seatsRepository.findById(seatId)
                    .orElseThrow(() -> new RuntimeException("Seat not found"));

            if (!seat.isAvailable()) {
                throw new RuntimeException("Seat " + seatId + " is not available");
            }

            if (seat.getBus().getBusNumber() != busNumber) {
                throw new RuntimeException("Seat " + seatId + " does not belong to bus number " + busNumber);
            }

            Booking booking = Booking.builder()
                    .id(transactionId)
                    .userId(userId)
                    .busNumber(busNumber)
                    .seatId(seatId)
                    .bookingDate(bookingDate)
                    .journeyDate(journeyDate)
                    .status("pending")
                    .build();

            seat.setAvailable(false);
            seatsRepository.save(seat);

            bookingRepository.save(booking);
        }

        return bookingRepository.findById(transactionId)
                .orElseThrow(() -> new RuntimeException("Booking not found"));
    }
}
