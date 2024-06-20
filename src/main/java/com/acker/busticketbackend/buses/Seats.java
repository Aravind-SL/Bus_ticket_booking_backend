package com.acker.busticketbackend.buses;

import com.fasterxml.jackson.annotation.JsonBackReference;
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
    @JsonBackReference
    private Bus bus;

    @Column(nullable = false)
    private int seatNumber;
}
