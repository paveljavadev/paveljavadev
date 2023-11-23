package ru.alishev.springcourse.FirstRestApp.models;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Entity
@Table(name = "Measure")
public class Measure {

        @Id
        @Column(name = "measure_id")
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private int id;

        @ManyToOne
        @JoinColumn(name = "sensor_name", referencedColumnName = "name")
        @NotNull(message = "Измерение не может быть без сенсора")
        private Sensor sensor;

        @Column (name = "value")
        @Min(value = -100, message = "Температура не может быть менее 100")
        @Max(value = 100, message = "Температура не может быть более 100")
        private float value;
        @Column (name = "raining")
        @NotNull (message = "Информация про осадки не может отсутствовать")
        private Boolean raining;

        @Column (name = "created_at")
        @Temporal(TemporalType.TIMESTAMP)
        private Date createdAt;


        public Measure() {
        }

        public Sensor getSensor() {
                return sensor;
        }

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

        public Date getCreatedAt() {
                return createdAt;
        }

        public void setCreatedAt(Date createdAt) {
                this.createdAt = createdAt;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public Measure(Sensor sensor, float value, boolean raining) {
                this.sensor = sensor;
                this.value = value;
                this.raining = raining;
        }
}
