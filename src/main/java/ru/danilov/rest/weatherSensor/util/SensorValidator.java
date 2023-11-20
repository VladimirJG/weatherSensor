package ru.danilov.rest.weatherSensor.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import ru.danilov.rest.weatherSensor.model.Sensor;
import ru.danilov.rest.weatherSensor.services.SensorService;

@Component
public class SensorValidator implements Validator {

    private final SensorService sensorService;

    @Autowired
    public SensorValidator(SensorService sensorService) {
        this.sensorService = sensorService;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return Sensor.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Sensor sensor = (Sensor) target;
        if (sensorService.getByName(sensor.getName()).isPresent()) {
            errors.rejectValue("name", "", "Сенсор с таким именем уже существует");
        }
    }
}
