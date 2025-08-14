package com.stefansandberg.demo.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.stefansandberg.demo.model.DeparturesResponse;
import com.stefansandberg.demo.model.SLStation;
import com.stefansandberg.demo.model.Station;
import org.springframework.beans.factory.annotation.Value;
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

    private String getDeparturesApiUrl(String stationId) {
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
            List<SLStation> slStations = this.objectMapper.readValue(jsonData, new TypeReference<List<SLStation>>(){});

            return slStations.stream()
                    .map(sl -> new Station(String.valueOf(sl.getId()), sl.getName()))
                    .collect(Collectors.toList());

        } catch (Exception e) {
            System.err.println("Error parsing JSON: " + e.getMessage());
            return List.of();
        }
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

    public DeparturesResponse getDeparturesForStation(String stationId) {
        String jsonData = apiService.fetchData(getDeparturesApiUrl(stationId));

        try {
            DeparturesResponse response = this.objectMapper.readValue(jsonData, DeparturesResponse.class);
            return response;
        } catch (Exception e) {
            System.err.println("Error parsing departures JSON: " + e.getMessage());
            return new DeparturesResponse(new ArrayList<>(), new ArrayList<>());
        }

    }
}
