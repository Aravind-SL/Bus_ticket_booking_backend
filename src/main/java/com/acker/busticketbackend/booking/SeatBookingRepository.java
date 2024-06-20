package com.acker.busticketbackend.booking;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SeatBookingRepository extends JpaRepository<SeatBooking, Long> {

    List<SeatBooking> findByBusIdAndSeatNumberAndJourneyDate(Long busId, int seatNumber, LocalDateTime journeyDate);
}
