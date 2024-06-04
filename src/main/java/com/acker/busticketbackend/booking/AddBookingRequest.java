package com.acker.busticketbackend.booking;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AddBookingRequest {
    private int userId;
    private int busNumber;
    private List<Integer> seatIds;
    private LocalDateTime journeyDate;
}
