package com.stefansandberg.demo.service;

import com.stefansandberg.demo.model.Station;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StationService {

    private final ApiService apiService;

    @Value("${sl.api.key}")
    private String apiKey;

    public StationService(ApiService apiService) {
        this.apiService = apiService;
    }

    private String getSLApiUrl() {
        return "https://api.sl.se/api2/LineData.json?model=site&key=" + apiKey;
    }

    public List<Station> getAllStations() {
        return List.of(
                new Station("9001", "T-Centralen"),
                new Station("9002", "Slussen"),
                new Station("9003", "Gamla Stan")
        );
    }

    public Station getStationById(String id) {
        List<Station> stations = getAllStations();

        for (Station station : stations) {
            if (station.getId().equals(id)) {
                return station;
            }
        }
        return null;
    }
}
