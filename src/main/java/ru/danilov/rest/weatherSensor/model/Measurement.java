package ru.danilov.rest.weatherSensor.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import org.springframework.lang.NonNull;

import java.time.LocalDateTime;

@Entity
@Table(name = "Measurement")
public class Measurement {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "value")
    @NotNull
    @Min(value = -100, message = "min value = -100")
    @Max(value = 100, message = "max value = 100")
    private Double value;
    @Column(name = "raining")
    @NotNull
    private Boolean raining;
    @NotNull
    @ManyToOne
    @JoinColumn(name = "sensor", referencedColumnName = "name")
    private Sensor sensor;
    @Column(name = "time_of_creation")
    private LocalDateTime timeOfCreation;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @NonNull
    public Double getValue() {
        return value;
    }

    public void setValue(@NonNull Double value) {
        this.value = value;
    }

    @NonNull
    public Boolean isRaining() {
        return raining;
    }

    public void setRaining(@NonNull Boolean raining) {
        this.raining = raining;
    }

    public Sensor getSensor() {
        return sensor;
    }

    public void setSensor(Sensor sensor) {
        this.sensor = sensor;
    }

    public LocalDateTime getTimeOfCreation() {
        return timeOfCreation;
    }

    public void setTimeOfCreation(LocalDateTime timeOfCreation) {
        this.timeOfCreation = timeOfCreation;
    }
}
