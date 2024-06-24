package com.acker.busticketbackend.routes;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Service
public class RoutesService {

    @Autowired
    private RoutesRepository routeRepository;

    public List<Routes> getAllRoutes() {
        return routeRepository.findAll();
    }

    public Routes getRouteById(Long routeId) {
        return routeRepository.findById(routeId)
                .orElseThrow(() -> new RuntimeException("Route not found"));
    }

    public Routes createRoute(Routes route) {
        return routeRepository.save(route);
    }

    public Routes updateRoute(Long routeId, Routes routeDetails) {
        Routes route = getRouteById(routeId);
        route.setFromStation(routeDetails.getFromStation());
        route.setToStation(routeDetails.getToStation());
        route.setDistance(routeDetails.getDistance());
        return routeRepository.save(route);
    }

    public void deleteRoute(Long routeId) {
        Routes route = getRouteById(routeId);
        routeRepository.delete(route);
    }

    public Optional<Routes> getByStationId(Integer start, Integer destination) {
        return routeRepository.findRouteFromStationToStation(start, destination);
    }
}
