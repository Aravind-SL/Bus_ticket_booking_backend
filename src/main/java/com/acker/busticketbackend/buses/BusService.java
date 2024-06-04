package com.acker.busticketbackend.buses;

import java.util.ArrayList;
import java.util.List;

import com.acker.busticketbackend.routes.RoutesService;
import com.acker.busticketbackend.seats.Seats;
import com.acker.busticketbackend.seats.SeatsRepository;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BusService {
    private final BusRepository busRepository;

    private final RoutesService routesService;

    private final SeatsRepository seatsRepository;

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

        List<Seats> seats = new ArrayList<>();
        for (int i = 1; i <= busRequest.getTotalSeats(); i++) {
            Seats seat = Seats.builder()
                    .bus(bus)
                    .seatNumber(i)
                    .isAvailable(true)
                    .build();
            seats.add(seat);
        }
        bus.setSeats(seats);

        Bus savedBus = busRepository.save(bus);
        seatsRepository.saveAll(seats);  

        return savedBus;
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
