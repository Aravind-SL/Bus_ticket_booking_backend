package com.acker.busticketbackend.routes;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Repository
public interface RoutesRepository extends JpaRepository<Routes, Long> {


    @Query("SELECT r FROM Routes r where r.fromStation = ?1 and r.toStation = ?2")
    Optional<Routes> findRouteFromStationToStation(Integer fromStationId, Integer toStationId);
}
