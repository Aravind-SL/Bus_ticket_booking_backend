package com.acker.busticketbackend.booking;

import com.acker.busticketbackend.auth.user.User;
import com.acker.busticketbackend.buses.Bus;
import com.acker.busticketbackend.buses.BusService;
import com.acker.busticketbackend.buses.Seats;
import com.acker.busticketbackend.buses.SeatsRepository;

import com.acker.busticketbackend.exceptions.InsufficientSeatsException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BookingService {

    private final BookingRepository bookingRepository;
    private final SeatBookingRepository seatBookingRepository;
    private final BusService busService;
    private final BookingProcessingResponseRepository bookingProcessingResponseRepository;

    public List<Booking> getAllBookings() {
        return bookingRepository.findAll();
    }

    @Transactional
    public Booking createBooking(long busId, int numberOfSeats, LocalDate journeyDate) {
        LocalDate bookingDate = LocalDate.now();
        // Take the user detail from the security context
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        // Get the bus object
        Bus bus = busService.getBusById(busId);

        // Check for availability of the seats on the specified journey date
        var seatsToBook = getAvailableSeats(
                bus, journeyDate, numberOfSeats
        );

        // Create Booking
        Booking booking = Booking.builder()
                .user(user)
                .bus(bus)
                .bookingDate(bookingDate)
                .journeyDate(journeyDate)
                .status(BookingStatus.PENDING)
                .build();

        // Save the booking
        Booking savedBooking = bookingRepository.save(booking);
        // Create and save SeatBooking entries

        List<SeatBooking> seatBookings = new java.util.ArrayList<>(List.of());
        for (Seats seat : seatsToBook) {
            SeatBooking seatBooking = SeatBooking.builder()
                    .booking(savedBooking)
                    .busId(bus.getBusId())
                    .seatNumber(seat.getSeatNumber())
                    .journeyDate(journeyDate)
                    .build();
            seatBookingRepository.save(seatBooking);
            seatBookings.add(seatBooking);
        }
        savedBooking.setSeatBookings(seatBookings);
        bookingRepository.save(savedBooking);

        return savedBooking;
    }


    public List<Seats> getAvailableSeats(
            Bus bus, LocalDate journeyDate, int n) throws InsufficientSeatsException {

        var bookedSeats = seatBookingRepository
                .findByBusIdAndJourneyDate(bus.getBusId(), journeyDate)
                .stream().map(SeatBooking::getSeatNumber)
                .collect(Collectors.toSet());

        var availableSeats = bus.getSeats().stream().filter(
                seat -> !bookedSeats.contains(seat.getSeatNumber())
        ).toList();

        if ((long) availableSeats.size() < n) throw new InsufficientSeatsException(
                bus.getBusId(), journeyDate, availableSeats.size(), n);

        return availableSeats.stream().limit(n).toList();
    }

    public List<SeatBooking> getBookedSeats() {
        return seatBookingRepository.findAll();
    }

    private boolean isSeatAvailable(Seats seat, LocalDate journeyDate) {
        List<SeatBooking> bookings = seatBookingRepository.findByBusIdAndSeatNumberAndJourneyDate(seat.getBus().getBusId(), seat.getSeatNumber(), journeyDate);
        return bookings.isEmpty();
    }

    public BookingProcessingResponse completeBooking(String bookingId, boolean approve, String message) {
        Booking booking = bookingRepository.findById(bookingId)
                .orElseThrow(() -> new RuntimeException("Booking Not exists: " + bookingId));

        booking.setStatus(approve ? BookingStatus.COMPLETE : BookingStatus.FAILURE);

        var seatBookings = booking.getSeatBookings();
        seatBookings.forEach(sb -> sb.setApproved(approve));
        seatBookingRepository.saveAll(seatBookings);

        BookingProcessingResponse res = BookingProcessingResponse.builder()
                .booking(booking)
                .approved(approve)
                .message(message)
                .build();

        bookingRepository.save(booking);

        return bookingProcessingResponseRepository.save(res);
    }

    public DailyBookingStatus getAvailableSeats(Long busId, LocalDate date) {
        Bus b = busService.getBusById(busId);
        var totalSeats = b.getTotalSeats();
        var res = seatBookingRepository.getAvailableSeatsOnDate(busId, date);
        System.out.println(res.getBookedSeats());
        
        return DailyBookingStatus.builder()
                .busId(b.getBusId())
                .seatsAvailable(totalSeats - res.getBookedSeats())
                .date(date)
                .build();
    }

    public List<DailyBookingStatus> getNextNDayBooking(Long busId, LocalDate fromDate, int days) {

        Bus b = busService.getBusById(busId);
        var totalSeats = b.getTotalSeats();
        var res = seatBookingRepository.getDailyBookingWithinRange(
                busId, fromDate.plusDays(1), fromDate.plusDays(days + 1)
        );

        var bookedByDate = new HashMap<LocalDate, Integer>();

        res.forEach(
                stat -> bookedByDate.put(stat.getJourneyDate(), stat.getBookedSeats())
        );

        List<DailyBookingStatus> seatStat = new java.util.ArrayList<>(List.of());

        System.out.println(bookedByDate);
        for (int i = 0; i < days; i++) {
            seatStat.add(
                    DailyBookingStatus.builder()
                            .busId(busId)
                            .seatsAvailable(
                                    totalSeats - bookedByDate.getOrDefault(
                                            fromDate.plusDays(1 + i), 0))
                            .date(fromDate.plusDays(1 + i))
                            .build()
            );

        }

        return seatStat;
    }
}
