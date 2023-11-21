package ru.danilov.rest.weatherSensor.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

public class SensorDTO {
    @NotEmpty(message = "Name not be empty")
    @Size(min = 10, max = 100, message = "Name should be between 10 to 100 characters")
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
