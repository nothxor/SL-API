package com.stefansandberg.sl.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Station {
    private int id;
    private String name;

    public Station(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public Station() {}

    public int getId() {
        return this.id;
    }

    public String getName() {
        return this.name;
    }
}
