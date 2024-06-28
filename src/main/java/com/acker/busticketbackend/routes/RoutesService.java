package com.acker.busticketbackend.routes;

import com.acker.busticketbackend.station.StationService;
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
    @Autowired
    private StationService stationService;

    public List<RouteResponse> getAllRoutes() {

        var routes = routeRepository.findAll();

        return routes.stream().map(rou -> RouteResponse.builder()
                .fromStation(Math.toIntExact(rou.getFromStation().getStationId()))
                .toStation(Math.toIntExact(rou.getToStation().getStationId()))
                .distance(rou.getDistance())
                .routeId(Math.toIntExact(rou.getRouteId()))
                .build()
        ).toList();
    }

    public Routes getRouteById(Long routeId) {
        return routeRepository.findById(routeId)
                .orElseThrow(() -> new RuntimeException("Route not found"));
    }

    public RouteResponse createRoute(AddUpdateRouteRequest route) {

        var startStation = stationService.getStationById((long) route.getFromStation());
        var toStation = stationService.getStationById((long) route.getToStation());

        var rou = routeRepository.save(
                Routes.builder()
                        .fromStation(startStation)
                        .toStation(toStation)
                        .distance(route.getDistance())
                        .build()
        );

        return RouteResponse.builder()
                .fromStation(Math.toIntExact(rou.getFromStation().getStationId()))
                .toStation(Math.toIntExact(rou.getToStation().getStationId()))
                .distance(rou.getDistance())
                .routeId(Math.toIntExact(rou.getRouteId()))
                .build();
    }

    public RouteResponse updateRoute(Long routeId, AddUpdateRouteRequest routeDetails) {
        Routes route = getRouteById(routeId);

        var startStation = stationService.getStationById((long) routeDetails.getFromStation());
        var toStation = stationService.getStationById((long) routeDetails.getToStation());

        route.setFromStation(startStation);
        route.setToStation(toStation);
        route.setDistance(routeDetails.getDistance());
        var rou = routeRepository.save(route);

        return RouteResponse.builder()
                .fromStation(Math.toIntExact(rou.getFromStation().getStationId()))
                .toStation(Math.toIntExact(rou.getToStation().getStationId()))
                .distance(rou.getDistance())
                .routeId(Math.toIntExact(rou.getRouteId()))
                .build();
    }

    public void deleteRoute(Long routeId) {
        Routes route = getRouteById(routeId);
        routeRepository.delete(route);
    }

    public Optional<Routes> getByStationId(Integer start, Integer destination) {

        var startStation = stationService.getStationById((long) start);
        var toStation = stationService.getStationById((long) destination);
        return routeRepository.findRouteFromStationToStation(startStation, toStation);


    }
}
