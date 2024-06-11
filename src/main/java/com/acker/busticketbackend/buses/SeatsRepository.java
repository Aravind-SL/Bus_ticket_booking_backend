package com.acker.busticketbackend.buses;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SeatsRepository extends JpaRepository<Seats, Integer> {
}
