package com.acker.busticketbackend.buses;

import java.time.LocalDateTime;
import java.util.List;

import com.acker.busticketbackend.routes.Routes;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIdentityReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
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

    /**
     * The Bus Number
     */
    @Column(nullable = false)
    private int busNumber;

    /**
     * The Route of the Bus
     */
    @ManyToOne
    @JoinColumn(name = "routeID", nullable = false)
    private Routes route;

    /**
     * The time the bus leaves the station
     */
    @Column(nullable = false)
    private LocalDateTime departureTime;

    /**
     * The time the bus arrives the destination
     */
    @Column(nullable = false)
    private LocalDateTime arrivalTime;

    /**
     * Total number of bookable seats
     */
    @Column(nullable = false)
    private int totalSeats;

    /**
     * The Bookable seats
     */
    @OneToMany(mappedBy = "bus", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
    @JsonIdentityReference(alwaysAsId = true)
    private List<Seats> seats;

    /**
     * The price of the ride in this for a unit distance. It determines the booking price
     */
    @Column(nullable = false)
    private Double pricePerUnitDistance;
}
