package com.acker.busticketbackend.buses;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BusService {
    @Autowired
    private final BusRepository busRepository;

    public List<Bus> getAllBuses() {
        return busRepository.findAll();
    }

    public Bus getBusById(Long busID) {
        return busRepository.findById(busID)
                .orElseThrow(() -> new RuntimeException("Bus not found"));
    }

    public Bus createBus(Bus bus) {
        return busRepository.save(bus);
    }

    public Bus updateBus(Long busID, Bus busDetails) {
        Bus bus = getBusById(busID);
        bus.setBusNumber(busDetails.getBusNumber());
        // bus.setRoute(busDetails.getRoute());
        bus.setDepartureTime(busDetails.getDepartureTime());
        bus.setArrivalTime(busDetails.getArrivalTime());
        bus.setTotalSeats(busDetails.getTotalSeats());
        return busRepository.save(bus);
    }

    public void deleteBus(Long busID) {
        Bus bus = getBusById(busID);
        busRepository.delete(bus);
    }
}
