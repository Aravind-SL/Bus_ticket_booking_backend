package com.acker.busticketbackend.booking;


import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.acker.busticketbackend.buses.Bus;
import com.acker.busticketbackend.buses.Seats;

@Repository
public interface BookingRepository extends JpaRepository<Booking, String>{
    @Query("SELECT b FROM Booking b JOIN b.seats s WHERE b.bus = :bus AND s = :seat AND b.journeyDate = :journeyDate")
    List<Booking> findByBusAndSeatsAndJourneyDate(@Param("bus") Bus bus, @Param("seat") Seats seat, @Param("journeyDate") LocalDateTime journeyDate);
}
