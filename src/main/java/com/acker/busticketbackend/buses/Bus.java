package com.acker.busticketbackend.buses;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

import com.acker.busticketbackend.routes.Routes;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Builder
@Table(name = "buses")
@AllArgsConstructor
@NoArgsConstructor
public class Bus {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(unique = true, nullable = false)
    private Long busId;

    @Column(nullable = false)
    private int busNumber;

    @ManyToOne
    @JoinColumn(name = "routeID", nullable = false)
    private Routes route;

    @Column(nullable = false, columnDefinition = "TIME")
    private LocalTime departureTime;

    @Column(nullable = false, columnDefinition = "TIME")
    private LocalTime arrivalTime;

    @Column(nullable = false)
    private int totalSeats;

    @OneToMany(mappedBy = "bus", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<Seats> seats;

    @Column(nullable = false)
    private Double pricePerUnitDistance;
}
