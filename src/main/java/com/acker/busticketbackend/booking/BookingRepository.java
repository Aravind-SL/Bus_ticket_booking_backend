package com.acker.busticketbackend.booking;

import com.acker.busticketbackend.auth.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookingRepository extends JpaRepository<Booking, String> {
    List<Booking> findByUser(User user);
}
