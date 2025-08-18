package com.stefansandberg.sl.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.time.LocalDateTime;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Departure {
    private String destination;
    private String display;
    private LocalDateTime scheduled;
    private LocalDateTime expected;
    private Line line;

    public Departure() {}

    public Departure(String destination, String display, LocalDateTime scheduled, LocalDateTime expected, Line line) {
        this.destination = destination;
        this.display = display;
        this.scheduled = scheduled;
        this.expected = expected;
        this.line = line;
    }

    public String getDestination() {
        return this.destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public String getDisplay() {
        return this.display;
    }

    public void setDisplay(String display) {
        this.display = display;
    }

    public LocalDateTime getScheduled() {
        return this.scheduled;
    }

    public void setScheduled(LocalDateTime scheduled) {
        this.scheduled = scheduled;
    }

    public LocalDateTime getExpected() {
        return this.expected;
    }

    public void setExpected(LocalDateTime expected) {
        this.expected = expected;
    }

    public Line getLine() {
        return this.line;
    }

    public void setLine(Line line) {
        this.line = line;
    }

}
