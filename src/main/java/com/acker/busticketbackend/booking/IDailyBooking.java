package com.acker.busticketbackend.booking;

import java.time.LocalDate;

public interface IDailyBooking {
    Long getBusId();

    LocalDate getJourneyDate();

    Integer getBookedSeats();
}
