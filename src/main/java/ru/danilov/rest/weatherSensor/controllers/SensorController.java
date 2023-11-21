package ru.danilov.rest.weatherSensor.controllers;

import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import ru.danilov.rest.weatherSensor.dto.SensorDTO;
import ru.danilov.rest.weatherSensor.exceptions.SensorNotCreatedException;
import ru.danilov.rest.weatherSensor.model.Sensor;
import ru.danilov.rest.weatherSensor.services.SensorService;
import ru.danilov.rest.weatherSensor.util.SensorValidator;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/sensor")
public class SensorController {
    private final SensorService sensorService;
    private final SensorValidator sensorValidator;
    private final ModelMapper modelMapper;


    public SensorController(SensorService sensorService, SensorValidator sensorValidator, ModelMapper modelMapper) {
        this.sensorService = sensorService;
        this.sensorValidator = sensorValidator;
        this.modelMapper = modelMapper;
    }

    @GetMapping()
    public List<SensorDTO> getSensors() {
        return sensorService.getAllSensors().stream()
                .map(this::convertToSensorDTO).collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public Sensor getOneSensor(@PathVariable("id") int id) {
        return sensorService.getSensor(id);
    }

    @PostMapping()
    public ResponseEntity<HttpStatus> createSensor(@RequestBody @Valid SensorDTO sensorDTO, BindingResult bindingResult) {
        Sensor sensor = convertToSensor(sensorDTO);
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
    public ResponseEntity<HttpStatus> update(@RequestBody @Valid SensorDTO sensorDTO, @PathVariable("id") int id, BindingResult bindingResult) {
        Sensor upSensor = convertToSensor(sensorDTO);
        sensorValidator.validate(upSensor, bindingResult);
        sensorService.update(upSensor, id);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") int id) {
        sensorService.delete(id);
        return "Sensor " + id + " deleted!";
    }

    private Sensor convertToSensor(SensorDTO sensorDTO) {
        return modelMapper.map(sensorDTO, Sensor.class);
    }

    private SensorDTO convertToSensorDTO(Sensor sensor) {
        return modelMapper.map(sensor, SensorDTO.class);
    }
}
