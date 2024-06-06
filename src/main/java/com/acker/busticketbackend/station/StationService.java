package com.acker.busticketbackend.station;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;

import java.util.List;

@Service
public class StationService {

    @Autowired
    private StationRepository stationRepository;

    @Transactional
    public List<Station> getAllStations() {
        return stationRepository.findAll();
    }

    @Transactional
    public Station getStationById(Long stationId) {
        return stationRepository.findById(stationId)
                .orElseThrow(() -> new RuntimeException("Station not found"));
    }

    @Transactional
    public Station createStation(Station station) {
        return stationRepository.save(station);
    }
    @Transactional
    public Station updateStation(Long stationId, Station stationDetails) {
        Station station = getStationById(stationId);
        station.setStationName(stationDetails.getStationName());
        station.setState(stationDetails.getState());
        return stationRepository.save(station);
    }

    @Transactional
    public void deleteStation(Long stationId) {
        Station station = getStationById(stationId);
        stationRepository.delete(station);
    }
}
