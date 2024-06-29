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

    /**
     * Creates new bus and save it in the busRepository
     *
     * @param busRequest The body of the request that has required information to create a bus
     * @return The created bus
     */
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

    /**
     * Update the details of the bus. This method will update every
     * detail of a bus for given busID except for the seats.
     *
     * @param busID      The ID of the bus that has to be updated
     * @param busDetails The new details of the bus
     * @return The updated bus
     */
    public Bus updateBus(Long busID, AddUpdateBusRequest busDetails) {
        Bus bus = getBusById(busID);
        bus.setBusNumber(busDetails.getBusNumber());
        bus.setRoute(routesService.getRouteById(busDetails.getRouteId()));
        bus.setDepartureTime(busDetails.getDepartureTime());
        bus.setArrivalTime(busDetails.getArrivalTime());
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
     * @return The bus with updated fare.
     */
    public Bus updateBusFare(Long id, UpdateFareRequest newFare) {
        Bus bus = getBusById(id);
        bus.setPricePerUnitDistance(newFare.getPricePerUnitDistance());
        return busRepository.save(bus);
    }

    /**
     * Search for bus by the from and to station
     *
     * @param startStation       The start of the ride
     * @param destinationStation The destination of the ride
     * @return list of buses that rides from startStation to destinationStation
     */
    public List<Bus> searchBusesByRoute(Integer startStation, Integer destinationStation) {

        var route = routesService.getByStationId(startStation, destinationStation);

        if (route.isEmpty())
            return List.of();


        return busRepository.findByRoutes(route.get());
    }
}
