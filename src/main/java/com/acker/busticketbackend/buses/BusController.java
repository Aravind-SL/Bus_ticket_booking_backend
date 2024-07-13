package com.acker.busticketbackend.buses;

import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/buses")
@CrossOrigin(origins = "*", maxAge = 3600)
@RequiredArgsConstructor
public class BusController {

    @Autowired
    private final BusService busService;

    @GetMapping
    public ResponseEntity<List<Bus>> getAllBuses() {
        return ResponseEntity.ok(busService.getAllBuses());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Bus> getBusById(@PathVariable Long id) {
        return ResponseEntity.ok(busService.getBusById(id));
    }

    @GetMapping("/search")
    public ResponseEntity<List<Bus>> searchBus(@RequestParam Integer start, @RequestParam Integer destination) {
        return ResponseEntity.ok(busService.searchBusesByRoute(start, destination));
    }

    @PostMapping
    public ResponseEntity<Bus> createBus(@RequestBody AddUpdateBusRequest bus) {

        return new ResponseEntity<>(busService.createBus(bus), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Bus> updateBus(@PathVariable Long id, @RequestBody AddUpdateBusRequest busDetails) {
        return ResponseEntity.ok(busService.updateBus(id, busDetails));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Bus> updateBusFare(@PathVariable Long id, @RequestBody UpdateFareRequest newFare) {
        return ResponseEntity.ok(busService.updateBusFare(id, newFare));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBus(@PathVariable Long id) {
        busService.deleteBus(id);
        return ResponseEntity.noContent().build();
    }
}