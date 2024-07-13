package com.acker.busticketbackend.buses;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.time.LocalTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AddUpdateBusRequest {
    private int busNumber;
    private long routeId;
    private LocalTime departureTime;
    private LocalTime arrivalTime;

    private int totalSeats;
    private Double pricePerUnitDistance;
}
