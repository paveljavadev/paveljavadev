package ru.alishev.springcourse.FirstRestApp.dto;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

public class SensorDTO {
    @NotEmpty(message = "Название сенсора не может быть пустым")
    @Size(min = 3, max = 30, message = "Название должно быть от 3 до 30 символов")
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
