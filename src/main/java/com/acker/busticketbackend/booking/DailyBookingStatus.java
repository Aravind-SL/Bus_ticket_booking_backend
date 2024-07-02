package com.acker.busticketbackend.booking;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DailyBookingStatus {
    private Long busId;
    private LocalDate date;
    private Integer seatsAvailable;
}
