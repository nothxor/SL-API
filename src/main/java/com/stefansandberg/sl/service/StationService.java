package com.stefansandberg.sl.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.stefansandberg.sl.model.Departure;
import com.stefansandberg.sl.model.DeparturesResponse;
import com.stefansandberg.sl.model.Station;
import com.stefansandberg.sl.model.enums.TransportMode;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class StationService {

    private final ApiService apiService;
    private final FileService fileService;
    private final ObjectMapper objectMapper;

    public StationService(ApiService apiService, FileService fileService, ObjectMapper objectMapper) {
        this.apiService = apiService;
        this.fileService = fileService;
        this.objectMapper = objectMapper;
    }

    private String getSLApiUrl() {
        return "https://transport.integration.sl.se/v1/sites";
    }

    private String getDeparturesApiUrl(int stationId) {
        return "https://transport.integration.sl.se/v1/sites/" + stationId + "/departures";
    }

    public List<Station> getAllStations() {

        String filename = "stations.json";

        String jsonData = fileService.readFromFile(filename);

        if (jsonData == null) {
            System.out.println("No cache found, calling API...");
            jsonData = apiService.fetchData(getSLApiUrl());
            fileService.saveToFile(jsonData, filename);
        }
        else {
            System.out.println("Using cached data from file.");
        }

        try {
            return this.objectMapper.readValue(jsonData, new TypeReference<List<Station>>() {});

        } catch (Exception e) {
            System.err.println("Error parsing JSON: " + e.getMessage());
            return List.of();
        }
    }

    public Station getStationById(int id) {
        List<Station> stations = getAllStations();

        for (Station station : stations) {
            if (station.getId() == id) {
                return station;
            }
        }
        return null;
    }

    public List<Station> getStationsByName(String name) {
        List<Station> stations = getAllStations();
        List<Station> stationsToReturn = new ArrayList<>();

        for (Station station : stations) {
            if (station.getName().toLowerCase().contains(name.toLowerCase())) {
                stationsToReturn.add(station);
            }
        }

        return stationsToReturn;
    }

    public DeparturesResponse getDeparturesForStation(int stationId, Integer limit, TransportMode transportMode) {
        String jsonData = apiService.fetchData(getDeparturesApiUrl(stationId));

        try {
            DeparturesResponse response = this.objectMapper.readValue(jsonData, DeparturesResponse.class);
            List<Departure> departures =  response.getDepartures();

            if (transportMode != null) {
                departures = filterByTransportMode(departures, transportMode);
            }

            if (limit != null) {
                departures = limitDepartures(departures, limit);
            }

            return new DeparturesResponse(departures, response.getStopDeviations());

        } catch (Exception e) {
            System.err.println("Error parsing departures JSON: " + e.getMessage());
            return new DeparturesResponse(new ArrayList<>(), new ArrayList<>());
        }
    }

    List<Departure> filterByTransportMode(List<Departure> departures, TransportMode mode) {
        if (departures == null) {
            return new ArrayList<>();
        }

        return departures.stream()
                .filter(departure -> {
                    TransportMode departureMode = departure.getLine().getTransportMode();
                    return departure.getLine() != null &&
                            departure.getLine().getTransportMode() != null &&
                            departure.getLine().getTransportMode() == mode;
                })
                .collect(Collectors.toList());
    }

    List<Departure> limitDepartures(List<Departure> departures, int limit) {
        if (limit <= 0) {
            return departures;
        }

        return departures.stream()
                .limit(limit)
                .collect(Collectors.toList());
    }
}
