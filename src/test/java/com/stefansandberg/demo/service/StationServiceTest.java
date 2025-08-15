package com.stefansandberg.demo.service;

import com.stefansandberg.demo.model.Departure;
import com.stefansandberg.demo.model.Line;
import com.stefansandberg.demo.model.enums.TransportMode;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class StationServiceTest {

    private Line metroLine;
    private Line busLine;
    private Line ferryLine;
    private Departure metroDeparture;
    private Departure busDeparture;
    private Departure ferryDeparture;
    private List<Departure> departures;
    private StationService stationService;

    @BeforeEach
    public void setUp() {
        metroLine = new Line("14", TransportMode.METRO);
        busLine = new Line("480", TransportMode.BUS);
        ferryLine = new Line("260", TransportMode.FERRY);

        metroDeparture = new Departure("Kungsträdgården", "Nu", null, null, metroLine);
        busDeparture = new Departure("Cityterminalen", "2 min", null, null, busLine);
        ferryDeparture = new Departure("Djurgården", "10 min", null, null, ferryLine);

        departures = Arrays.asList(metroDeparture, busDeparture, ferryDeparture);

        stationService = new StationService(null, null, null);
    }

    @Test
    public void testFilterByTransportMode_ReturnsMatchingDepartures() {
        List<Departure> result = stationService.filterByTransportMode(departures, TransportMode.METRO);

        assertEquals(1, result.size());
        assertEquals("Kungsträdgården", result.get(0).getDestination());
    }

    @Test
    public void testFilterByTransportMode_NoMatches_ReturnsEmptyList() {
        List<Departure> result = stationService.filterByTransportMode(departures, TransportMode.TAXI);

        assertEquals(0, result.size());
    }

    @Test
    public void testFilterByTransportMode_NullInput_ReturnsEmptyList() {
        List<Departure> result = stationService.filterByTransportMode(null, TransportMode.METRO);

        assertEquals(0, result.size());
    }

    @Test
    public void testFilterByTransportMode_EmptyList_ReturnsEmptyList() {
        List<Departure> emptyDepartures = new ArrayList<>();

        List<Departure> result = stationService.filterByTransportMode(emptyDepartures, TransportMode.METRO);

        assertEquals(0, result.size());
    }

    @Test
    public void testLimitDepartures_NormalCase() {
        List<Departure> result = stationService.limitDepartures(departures, 2);

        assertEquals(2, result.size());
        assertEquals("Kungsträdgården", result.get(0).getDestination());
        assertEquals("Cityterminalen", result.get(1).getDestination());
    }

    @Test
    public void testLimitDepartures_LimitLargerThanListSize() {
        List<Departure> result = stationService.limitDepartures(departures, 4);

        assertEquals(3, result.size());
    }

    @Test
    public void testLimitDepartures_LimitBelowZero() {
        List<Departure> result = stationService.limitDepartures(departures, -2);

        assertEquals(3, result.size());
    }

    @Test
    public void testLimitDepartures_ZeroLimit() {
        List<Departure> result = stationService.limitDepartures(departures, 0);

        assertEquals(3, result.size());
    }

    @Test
    public void testLimitDepartures_LimitSameAsListSize() {
        List<Departure> result = stationService.limitDepartures(departures, 3);

        assertEquals(3, result.size());
    }

    @Test
    public void testLimitDepartures_NullDeparturesList() {
        List<Departure> nullDepartures = new ArrayList<>();

        List<Departure> result = stationService.limitDepartures(nullDepartures, 3);

        assertEquals(0, result.size());
    }
}
