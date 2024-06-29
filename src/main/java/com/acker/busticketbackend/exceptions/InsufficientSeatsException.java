package com.acker.busticketbackend.exceptions;

import java.time.LocalDate;

public class InsufficientSeatsException extends RuntimeException {
    /**
     * This exception is thrown if the number of seats requested for booking
     * exceeds the number of seats available for the date, bus
     */
    public InsufficientSeatsException(
            Long busID,
            LocalDate journeyDate,
            int noOfSeatsAvailable,
            int noOfSeatsRequested
    ) {
        super("The Bus Id " +
                busID +
                " has " +
                noOfSeatsAvailable +
                " seats, but requested " +
                noOfSeatsRequested +
                ". Journey Date: " + journeyDate
        );
    }

}
