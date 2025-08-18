package com.stefansandberg.sl.model.enums;

import com.fasterxml.jackson.annotation.JsonCreator;

public enum TransportMode {
    BUS,
    TRAM,
    METRO,
    TRAIN,
    FERRY,
    SHIP,
    TAXI,
    OTHER;

    @JsonCreator
    public static TransportMode fromString(String value) {
        if (value == null) {
            return OTHER;
        }

        try {
            return TransportMode.valueOf(value);
        } catch (IllegalArgumentException e) {
            return OTHER;
        }
    }
}
