package ru.danilov.rest.weatherSensor.exceptions;

public class MeasurementException extends RuntimeException {
    public MeasurementException(String message) {
        super(message);
    }
}
