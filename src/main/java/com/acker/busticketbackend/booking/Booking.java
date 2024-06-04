package com.acker.busticketbackend.booking;


import java.time.LocalDateTime;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
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
    private int userId;

    @Column(nullable = false)
    private int busNumber;

    @Column(nullable = false)
    private int seatId;

    @Column(nullable = false)
    private LocalDateTime bookingDate;

    @Column(nullable = false)
    private LocalDateTime journeyDate;

    @Column(nullable = false)
    private String status;

}
