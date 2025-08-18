package com.stefansandberg.sl.model;

public class HelloResponse {
    private String message;

    public HelloResponse(String message) {
        this.message = message;
    }

    public String getMessage() {
        return this.message;
    }
}
