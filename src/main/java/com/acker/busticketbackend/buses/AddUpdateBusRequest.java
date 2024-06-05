package com.acker.busticketbackend.buses;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AddUpdateBusRequest {
    private int busNumber;
    private long routeId;
    private LocalDateTime departureTime;
    private LocalDateTime arrivalTime;
    private int totalSeats;
    private float pricePerUnitDistance;
}
