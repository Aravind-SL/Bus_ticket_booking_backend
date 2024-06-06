package com.acker.busticketbackend.booking;


import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;


import com.acker.busticketbackend.auth.user.User;
import com.acker.busticketbackend.buses.Bus;
import com.acker.busticketbackend.buses.Seats;
import com.fasterxml.jackson.annotation.JsonManagedReference;
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
    @Column(unique = true, nullable = false)
    private String id;

    @Column(nullable = false)
    @ManyToOne
    private User user;

    @Column(nullable = false)
    @ManyToOne
    @JoinColumn(name = "busId", nullable = false)
    private Bus bus;

    @ManyToMany
    @Column(nullable = false)
    @JoinColumn(name = "seatsId", nullable = false)
    @JsonManagedReference
    private List<Seats> seats;

    @Column(nullable = false)
    private LocalDateTime bookingDate;

    @Column(nullable = false)
    private LocalDateTime journeyDate;

    @Column(nullable = false)
    @Enumerated(value = EnumType.STRING)
    private BookingStatus status;

}
