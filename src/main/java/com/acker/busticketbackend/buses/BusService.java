package com.acker.busticketbackend.buses;

import java.util.*;

import com.acker.busticketbackend.routes.RoutesService;

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
    public Bus createBus(AddUpdateBusRequest busRequest) {
        Bus bus = Bus.builder()
                .route(routesService.getRouteById(busRequest.getRouteId()))
                .busNumber(busRequest.getBusNumber())
                .arrivalTime(busRequest.getArrivalTime())
                .departureTime(busRequest.getDepartureTime())
                .totalSeats(busRequest.getTotalSeats())
                .pricePerUnitDistance(busRequest.getPricePerUnitDistance())
                .build();

        // Create Bus
        busRepository.save(bus);

        // Add Seats
        Set<Seats> seats = new HashSet<>();
        for (int i = 1; i <= busRequest.getTotalSeats(); i++) {
            Seats seat = Seats.builder()
                    .bus(bus)
                    .seatNumber(i)
                    .build();
            seats.add(seat);
        }

        bus.setSeats(seatsRepository.saveAll(seats));

        return busRepository.save(bus);
    }

    //Requires FineTuning now it requires all parameter to update
    // FineTuning ???
    public Bus updateBus(Long busID, AddUpdateBusRequest busDetails) {
        Bus bus = getBusById(busID);
        bus.setBusNumber(busDetails.getBusNumber());
        bus.setRoute(routesService.getRouteById(busDetails.getRouteId()));
        bus.setDepartureTime(busDetails.getDepartureTime());
        bus.setArrivalTime(busDetails.getArrivalTime());
        bus.setTotalSeats(busDetails.getTotalSeats());
        bus.setPricePerUnitDistance(busDetails.getPricePerUnitDistance());
        return busRepository.save(bus);
    }

    /**
     * Deletes the bus from the database
     *
     * @param busID the id of the bus that should be deleted.
     */
    public void deleteBus(Long busID) {
        Bus bus = getBusById(busID);
        busRepository.delete(bus);
    }

    /**
     * Returns new Bus object with new fare.
     *
     * @param id      the id of the bus.
     * @param newFare the body of the request with new pricePerUnitDistance
     */
    public Bus updateBusFare(Long id, UpdateFareRequest newFare) {
        Bus bus = getBusById(id);
        bus.setPricePerUnitDistance(newFare.getPricePerUnitDistance());
        return busRepository.save(bus);
    }

    public List<Bus> searchBusesByRoute(Integer startStation, Integer destinationStation) {

        var route = routesService.getByStationId(startStation, destinationStation);

        if (route.isEmpty())
            return List.of();


        return busRepository.findByRoutes(route.get());
    }
}
