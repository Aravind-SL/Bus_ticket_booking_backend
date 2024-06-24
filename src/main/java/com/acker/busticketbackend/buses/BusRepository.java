package com.acker.busticketbackend.buses;


import com.acker.busticketbackend.routes.Routes;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BusRepository extends JpaRepository<Bus, Long> {
    @Query("SELECT b FROM Bus b WHERE b.route = ?1")
    List<Bus> findByRoutes(Routes routes);
}