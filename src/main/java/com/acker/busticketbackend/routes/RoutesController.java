package com.acker.busticketbackend.routes;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/routes")
@CrossOrigin(maxAge = 3600)
public class RoutesController {

    @Autowired
    private RoutesService routeService;

    @GetMapping
    public ResponseEntity<List<RouteResponse>> getAllRoutes() {
        return ResponseEntity.ok(routeService.getAllRoutes());
    }

    @GetMapping("/{id}")
    public ResponseEntity<RouteResponse> getRouteById(@PathVariable Long id) {
        var rou = routeService.getRouteById(id);
        return ResponseEntity.ok(RouteResponse.builder()
                .fromStation(Math.toIntExact(rou.getFromStation().getStationId()))
                .toStation(Math.toIntExact(rou.getToStation().getStationId()))
                .distance(rou.getDistance())
                .routeId(Math.toIntExact(rou.getRouteId()))
                .build()
        );
    }

    @PostMapping
    public ResponseEntity<?> createRoute(@RequestBody AddUpdateRouteRequest route) {
        if (route.getFromStation() == null) {
            return ResponseEntity.badRequest().body("fromStation cannot be null");
        }
        return new ResponseEntity<RouteResponse>(routeService.createRoute(route), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateRoute(@PathVariable Long id, @RequestBody AddUpdateRouteRequest routeDetails) {
        if (routeDetails.getFromStation() == null) {
            return ResponseEntity.badRequest().body("fromStation cannot be null");
        }
        return ResponseEntity.ok(routeService.updateRoute(id, routeDetails));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRoute(@PathVariable Long id) {
        routeService.deleteRoute(id);
        return ResponseEntity.noContent().build();
    }
}


