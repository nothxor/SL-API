package com.stefansandberg.demo.controller;

import com.stefansandberg.demo.model.DeparturesResponse;
import com.stefansandberg.demo.model.Station;
import com.stefansandberg.demo.service.StationService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import org.springframework.http.ResponseEntity;

import java.util.List;

@RestController
public class StationController {
    private final StationService stationService;

    public StationController(StationService stationService) {
        this.stationService = stationService;
    }

    @GetMapping("/stations")
    public List<Station> getStations(@RequestParam(required = false) String name) {
        if (name != null) {
            return stationService.getStationsByName(name);
        }
        return stationService.getAllStations();
    }

    @GetMapping("/stations/{id}")
    public ResponseEntity<Station> getStation(@PathVariable String id) {
        Station station = stationService.getStationById(id);
        if (station == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(station);
    }

    @GetMapping("/stations/{id}/departures")
    public DeparturesResponse getDepartures(@PathVariable String id) {
        return stationService.getDeparturesForStation(id);
    }
}
