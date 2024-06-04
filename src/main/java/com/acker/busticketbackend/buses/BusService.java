package com.acker.busticketbackend.buses;

import java.util.List;

import com.acker.busticketbackend.routes.RoutesRepository;
import com.acker.busticketbackend.routes.RoutesService;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BusService {
    private final BusRepository busRepository;

    private final RoutesService routesService;

    public List<Bus> getAllBuses() {
        return busRepository.findAll();
    }

    public Bus getBusById(Long busID) {
        return busRepository.findById(busID)
                .orElseThrow(() -> new RuntimeException("Bus not found"));
    }

    /* Creates New Bus */
    public Bus createBus(AddBusRequest busRequest) {

        Bus bus = Bus.builder()
                .route(routesService.getRouteById(busRequest.getRouteId()))
                .busNumber(busRequest.getBusNumber())
                .arrivalTime(busRequest.getArrivalTime())
                .departureTime(busRequest.getDepartureTime())
                .totalSeats(busRequest.getTotalSeats())
                .build();

        return busRepository.save(bus);
    }

    //Requires FineTuning now it requires all parameter to update
    public Bus updateBus(Long busID, Bus busDetails) {
        Bus bus = getBusById(busID);
        bus.setBusNumber(busDetails.getBusNumber());
        bus.setRoute(busDetails.getRoute());
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
