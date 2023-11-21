package ru.danilov.rest.weatherSensor.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;

public class MeasurementDTO {
    @NotEmpty(message = "Value must not be empty")
    @Min(value = -100, message = "min value = -100")
    @Max(value = 100, message = "max value = 100")
    private Double value;

    @NotEmpty(message = "Raining must not be empty")
    private String raining;

    public Double getValue() {
        return value;
    }

    public void setValue(Double value) {
        this.value = value;
    }

    public String getRaining() {
        return raining;
    }

    public void setRaining(String raining) {
        this.raining = raining;
    }
}
