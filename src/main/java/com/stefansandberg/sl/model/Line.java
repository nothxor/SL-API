package com.stefansandberg.sl.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.stefansandberg.sl.model.enums.TransportMode;

public class Line {
    private String designation;

    @JsonProperty("transport_mode")
    private TransportMode transportMode;

    public Line(String designation, TransportMode transportMode) {
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

    public TransportMode getTransportMode() {
        return this.transportMode;
    }

    public void setTransportMode(TransportMode transportMode) {
        this.transportMode = transportMode;
    }
}
