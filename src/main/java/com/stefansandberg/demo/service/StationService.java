package com.stefansandberg.demo.service;

import com.stefansandberg.demo.model.Station;

import java.util.List;

public class StationService {

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
