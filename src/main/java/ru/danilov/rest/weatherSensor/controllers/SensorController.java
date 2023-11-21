package ru.danilov.rest.weatherSensor.controllers;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import ru.danilov.rest.weatherSensor.exceptions.SensorNotCreatedException;
import ru.danilov.rest.weatherSensor.model.Sensor;
import ru.danilov.rest.weatherSensor.services.SensorService;
import ru.danilov.rest.weatherSensor.util.SensorValidator;

import java.util.List;

@RestController
@RequestMapping("/sensor")
public class SensorController {
    private final SensorService sensorService;
    private final SensorValidator sensorValidator;


    public SensorController(SensorService sensorService, SensorValidator sensorValidator) {
        this.sensorService = sensorService;
        this.sensorValidator = sensorValidator;
    }

    @GetMapping()
    public List<Sensor> getSensors() {
        return sensorService.getAllSensors();
    }

    @GetMapping("/{id}")
    public Sensor getOneSensor(@PathVariable("id") int id) {
        return sensorService.getSensor(id);
    }

    @PostMapping()
    public ResponseEntity<HttpStatus> createSensor(@RequestBody @Valid Sensor sensor, BindingResult bindingResult) {
        sensorValidator.validate(sensor, bindingResult);
        if (bindingResult.hasErrors()) {
            StringBuilder errorMsg = new StringBuilder();

            List<FieldError> errors = bindingResult.getFieldErrors();
            for (FieldError error : errors) {
                errorMsg.append(error.getField())
                        .append(" - ")
                        .append(error.getDefaultMessage())
                        .append(";");
            }
            throw new SensorNotCreatedException(errorMsg.toString());
        }
        sensorService.save(sensor);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<HttpStatus> update(@RequestBody @Valid Sensor upSensor, @PathVariable("id") int id, BindingResult bindingResult) {
        sensorValidator.validate(upSensor, bindingResult);
        sensorService.update(upSensor, id);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") int id) {
        sensorService.delete(id);
        return "Sensor " + id +" deleted!";
    }
}
