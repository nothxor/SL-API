package com.stefansandberg.demo.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Line {
    private String designation;

    @JsonProperty("transport_mode")
    private String transportMode;

    public Line(String designation, String transportMode) {
        this.designation = designation;
        this.transportMode = transportMode;
    }

    public Line() {}

    public String getDesignation() {
        return this.designation;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }

    public String getTransportMode() {
        return this.transportMode;
    }

    public void setTransportMode(String transportMode) {
        this.transportMode = transportMode;
    }
}
