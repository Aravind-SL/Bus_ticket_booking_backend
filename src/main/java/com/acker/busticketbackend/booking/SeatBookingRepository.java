package com.acker.busticketbackend.booking;

import com.acker.busticketbackend.buses.Bus;
import com.acker.busticketbackend.buses.Seats;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface SeatBookingRepository extends JpaRepository<SeatBooking, Long> {

    @Query("SELECT sb FROM SeatBooking sb WHERE sb.bus = :bus AND sb.seat = :seat AND sb.journeyDate = :journeyDate")
    List<SeatBooking> findByBusAndSeatAndJourneyDate(@Param("bus") Bus bus, @Param("seat") Seats seat, @Param("journeyDate") LocalDateTime journeyDate);

    @Query("SELECT sb.seat FROM SeatBooking sb WHERE sb.bus = :bus AND sb.journeyDate = :journeyDate")
    List<Seats> findBookedSeatsByBusAndJourneyDate(@Param("bus") Bus bus, @Param("journeyDate") LocalDateTime journeyDate);
}
