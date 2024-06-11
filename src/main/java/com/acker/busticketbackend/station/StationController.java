package com.acker.busticketbackend.station;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/stations")
public class StationController {

    @Autowired
    private StationService stationService;

    @GetMapping
    public ResponseEntity<List<Station>> getAllStations() {
        return ResponseEntity.ok(stationService.getAllStations());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Station> getStationById(@PathVariable Long id) {
        return ResponseEntity.ok(stationService.getStationById(id));
    }

    @PostMapping
    public ResponseEntity<Station> createStation(@RequestBody Station station) {
        return new ResponseEntity<>(stationService.createStation(station), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Station> updateStation(@PathVariable Long id, @RequestBody Station stationDetails) {
        return ResponseEntity.ok(stationService.updateStation(id, stationDetails));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteStation(@PathVariable Long id) {
        stationService.deleteStation(id);
        return ResponseEntity.noContent().build();
    }
}
