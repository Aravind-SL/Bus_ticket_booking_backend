package com.acker.busticketbackend.booking;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;

import java.util.Set;

import com.acker.busticketbackend.auth.user.User;
import com.acker.busticketbackend.buses.Bus;

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
@Table(name = "booking")
@AllArgsConstructor
@NoArgsConstructor
public class Booking {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(unique = true, nullable = false)
    private String id;

    @ManyToOne
    @JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
    @JsonIdentityReference(alwaysAsId = true)
    private User user;

    @ManyToOne
    @JoinColumn(name = "busId", nullable = false)
    @JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "busId")
    @JsonIdentityReference(alwaysAsId = true)
    private Bus bus;

    @OneToMany(mappedBy = "booking", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
    @JsonIdentityReference(alwaysAsId = true)
    @Builder.Default
    private List<SeatBooking> seatBookings = List.of();

    @Column(nullable = false, columnDefinition = "DATE")
    private LocalDate bookingDate;

    @Column(nullable = false, columnDefinition = "DATE")
    private LocalDate journeyDate;

    @Column(nullable = false)
    @Enumerated(value = EnumType.STRING)
    private BookingStatus status;
}
