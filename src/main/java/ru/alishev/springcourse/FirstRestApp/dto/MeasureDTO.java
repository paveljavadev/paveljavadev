package ru.alishev.springcourse.FirstRestApp.dto;

import ru.alishev.springcourse.FirstRestApp.models.Sensor;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Date;

public class MeasureDTO {


    @Min(value = -100, message = "Температура не может быть менее 100")
    @Max(value = 100, message = "Температура не может быть более 100")
    private float value;

    @NotNull(message = "Cannot be null")
    private Boolean raining;
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    private Sensor sensor;

//    public Sensor getSensor() {
//        return sensor;
//    }


    public String getName() {
        return sensor.getName();
    }
//
//    public void setSensorName(String name) {
//        this.sensor.getSensorName() = name;
//    }
    public void setSensor(Sensor sensor) {
        this.sensor = sensor;
    }

    public float getValue() {
        return value;
    }

    public void setValue(float value) {
        this.value = value;
    }


    public Boolean isRaining() {
        return raining;
    }

    public void setRaining(Boolean raining) {
        this.raining = raining;
    }
}
