package ru.danilov.rest.weatherSensor.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import ru.danilov.rest.weatherSensor.model.Measurement;
import ru.danilov.rest.weatherSensor.services.SensorService;

@Component
public class MeasurementValidator implements Validator {
    private final SensorService sensorService;

    @Autowired
    public MeasurementValidator(SensorService sensorService) {
        this.sensorService = sensorService;
    }


    @Override
    public boolean supports(Class<?> clazz) {
        return Measurement.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Measurement measurement = (Measurement) target;
        if (measurement.getSensor() == null) {
            return;
        }
        if (sensorService.getByName(measurement.getSensor().getName()).isEmpty()) {
            errors.rejectValue("sensor", "Нет зарегистрированного сенсора с таким именем!");
        }
    }
}
