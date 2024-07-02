package com.acker.busticketbackend.booking;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface SeatBookingRepository extends JpaRepository<SeatBooking, Long> {

    List<SeatBooking> findByBusIdAndSeatNumberAndJourneyDate(Long busId, int seatNumber, LocalDate journeyDate);

    Set<SeatBooking> findByBusIdAndJourneyDate(Long busId, LocalDate journeyDate);

    @Query("SELECT sb.busId AS busId, sb.journeyDate AS journeyDate, COUNT(*) AS bookedSeats" +
            " FROM SeatBooking sb WHERE sb.busId = ?1 and sb.journeyDate between ?2 and ?3 GROUP BY (sb.journeyDate, sb.busId)")
    List<IDailyBooking> getDailyBookingWithinRange(Long busId, LocalDate fromDate, LocalDate toDate);

    @Query(value = "SELECT COUNT(*) AS bookedSeats" +
            " FROM seat_bookings sb WHERE sb.bus_id = ?1 and sb.journey_date = ?2  ", nativeQuery = true)
    IDailyBooking getAvailableSeatsOnDate(Long busId, LocalDate date);
}
