package com.acker.busticketbackend.buses;

import java.util.Set;

import com.acker.busticketbackend.booking.SeatBooking;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIdentityReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Builder
@Table(name = "seats")
@AllArgsConstructor
@NoArgsConstructor
public class Seats {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(unique = true, nullable = false)
    private int id;

    @ManyToOne
    @JoinColumn(name = "busId", nullable = false)
    private Bus bus;

    @Column(nullable = false)
    private int seatNumber;

}
