package ru.alishev.springcourse.FirstRestApp.utils;

public class SensorErrorResponse {
    private String message;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public SensorErrorResponse(String message) {
        this.message = message;
    }
}
