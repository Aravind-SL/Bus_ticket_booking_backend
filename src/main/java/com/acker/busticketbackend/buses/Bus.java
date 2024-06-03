package com.acker.busticketbackend.buses;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Builder
@Table(name = "Buses")
@AllArgsConstructor
@NoArgsConstructor
public class Bus {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(unique = true, nullable = false)
    private Long busId;
    @Column(nullable=false)
    private int busNumber;

    //Private Route route;
    @Column(nullable=false)
    private LocalDateTime departureTime;
    @Column(nullable=false)
    private LocalDateTime arrivalTime;
    @Column(nullable=false)
    private int totalSeats;

}
