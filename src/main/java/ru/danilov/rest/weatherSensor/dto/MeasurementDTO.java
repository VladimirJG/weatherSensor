package ru.danilov.rest.weatherSensor.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import ru.danilov.rest.weatherSensor.model.Sensor;

public class MeasurementDTO {
    @NotNull
    @Min(value = -100, message = "min value = -100")
    @Max(value = 100, message = "max value = 100")
    private Double value;

    @NotNull
    private String isRaining;
    @NotNull
    private Sensor sensor;

    public Double getValue() {
        return value;
    }

    public void setValue(Double value) {
        this.value = value;
    }

    public String isRaining() {
        return isRaining;
    }

    public void setRaining(String raining) {
        this.isRaining = raining;
    }

    public Sensor getSensor() {
        return sensor;
    }

    public void setSensor(Sensor sensor) {
        this.sensor = sensor;
    }
}
