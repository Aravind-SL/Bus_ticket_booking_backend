package com.acker.busticketbackend.booking;

import com.acker.busticketbackend.auth.user.User;
import com.acker.busticketbackend.users.UserResponse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BookingResponse {

    private String id;
    private UserResponse user;
    private List<SeatBooking> seatBookings;
    private LocalDate bookingDate;
    private LocalDate journeyDate;
    private BookingStatus status;
    private Long busId;

}


