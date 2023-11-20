package ru.danilov.rest.weatherSensor.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;

import java.time.LocalDateTime;

@Entity
@Table(name = "Measurement")
public class Measurement {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "value")
    @NotEmpty(message = "Value must not be empty")
    @Min(value = -100, message = "min value = -100")
    @Max(value = 100, message = "max value = 100")
    private double value;
    @Column(name = "raining")
    @NotEmpty(message = "Raining must not be empty")
    private String raining;
    @ManyToOne
    @JoinColumn(name = "sensor_id",  referencedColumnName = "id")
    private Sensor sensorOwner;
    @Column(name = "time_of_creation")
    private LocalDateTime timeOfCreation;

    public Measurement() {
    }

    public Measurement(double value, String raining) {
        this.value = value;
        this.raining = raining;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }

    public String getRaining() {
        return raining;
    }

    public void setRaining(String raining) {
        this.raining = raining;
    }

    public Sensor getSensorOwner() {
        return sensorOwner;
    }

    public void setSensorOwner(Sensor sensorOwner) {
        this.sensorOwner = sensorOwner;
    }

    public LocalDateTime getTimeOfCreation() {
        return timeOfCreation;
    }

    public void setTimeOfCreation(LocalDateTime timeOfCreation) {
        this.timeOfCreation = timeOfCreation;
    }
}
