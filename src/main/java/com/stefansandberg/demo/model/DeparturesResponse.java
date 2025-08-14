package com.stefansandberg.demo.model;

import java.util.List;

public class DeparturesResponse {

    private List<Departure> departures;
    private List<Object> stopDeviations;

    public DeparturesResponse() {}

    public DeparturesResponse(List<Departure> departures, List<Object> stopDeviations) {
        this.departures = departures;
        this.stopDeviations = stopDeviations;
    }

    public List<Departure> getDepartures() {
        return this.departures;
    }

    public void setDepartures(List<Departure> departures) {
        this.departures = departures;
    }

    public List<Object> getStopDeviations() {
        return this.stopDeviations;
    }

    public void setStopDeviations(List<Object> stopDeviations) {
        this.stopDeviations = stopDeviations;
    }
}
