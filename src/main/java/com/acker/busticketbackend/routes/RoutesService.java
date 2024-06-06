package com.acker.busticketbackend.routes;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;

import java.util.List;

@Service
public class RoutesService {

    @Autowired
    private RoutesRepository routeRepository;

    @Transactional
    public List<Routes> getAllRoutes() {
        return routeRepository.findAll();
    }

    @Transactional
    public Routes getRouteById(Long routeId) {
        return routeRepository.findById(routeId)
                .orElseThrow(() -> new RuntimeException("Route not found"));
    }

    @Transactional
    public Routes createRoute(Routes route) {
        return routeRepository.save(route);
    }

    @Transactional
    public Routes updateRoute(Long routeId, Routes routeDetails) {
        Routes route = getRouteById(routeId);
        route.setFromStation(routeDetails.getFromStation());
        route.setToStation(routeDetails.getToStation());
        route.setDistance(routeDetails.getDistance());
        return routeRepository.save(route);
    }

    @Transactional
    public void deleteRoute(Long routeId) {
        Routes route = getRouteById(routeId);
        routeRepository.delete(route);
    }
}
