package ru.alishev.springcourse.FirstRestApp.utils;

public class MeasureErrorResponse {
    private String message;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public MeasureErrorResponse(String message) {
        this.message = message;
    }
}
