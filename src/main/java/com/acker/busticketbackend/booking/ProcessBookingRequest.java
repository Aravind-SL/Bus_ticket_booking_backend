package com.acker.busticketbackend.booking;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProcessBookingRequest {
    private String message;
    private String bookingId;
    private boolean approved;
}
