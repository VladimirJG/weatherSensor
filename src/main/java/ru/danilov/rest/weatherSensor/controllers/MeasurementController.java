package ru.danilov.rest.weatherSensor.controllers;

import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.danilov.rest.weatherSensor.dto.MeasurementDTO;
import ru.danilov.rest.weatherSensor.dto.MeasurementResponse;
import ru.danilov.rest.weatherSensor.exceptions.ErrorResponse;
import ru.danilov.rest.weatherSensor.exceptions.MeasurementException;
import ru.danilov.rest.weatherSensor.model.Measurement;
import ru.danilov.rest.weatherSensor.services.MeasurementService;
import ru.danilov.rest.weatherSensor.util.MeasurementValidator;

import java.util.stream.Collectors;

import static ru.danilov.rest.weatherSensor.util.ErrorsUtil.returnErrorsToClient;

@RestController
@RequestMapping("/measurement")
public class MeasurementController {
    private final MeasurementService measurementService;
    private final MeasurementValidator measurementValidator;
    private final ModelMapper modelMapper;

    public MeasurementController(MeasurementService measurementService, MeasurementValidator measurementValidator, ModelMapper modelMapper) {
        this.measurementService = measurementService;
        this.measurementValidator = measurementValidator;
        this.modelMapper = modelMapper;
    }

    @GetMapping()
    public MeasurementResponse getAll() {
        return new MeasurementResponse(measurementService.getAllMeasurements().stream()
                .map(this::convertToMeasurementDTO).collect(Collectors.toList()));
    }

    @PostMapping("/add")
    public ResponseEntity<HttpStatus> add(@RequestBody @Valid MeasurementDTO measurementDTO, BindingResult bindingResult) {
        Measurement measurementToAdd = convertToMeasurement(measurementDTO);
        measurementValidator.validate(measurementToAdd, bindingResult);
        if (bindingResult.hasErrors()) {
            returnErrorsToClient(bindingResult);
        }
        measurementService.saveMeasurement(measurementToAdd);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @GetMapping("/rainyDaysCount")
    public Long getRainyDaysCount() {
        return measurementService.getAllMeasurements().stream().filter(Measurement::isRaining).count();
    }

    private Measurement convertToMeasurement(MeasurementDTO measurementDTO) {
        return modelMapper.map(measurementDTO, Measurement.class);
    }

    private MeasurementDTO convertToMeasurementDTO(Measurement measurement) {
        return modelMapper.map(measurement, MeasurementDTO.class);
    }

    @ExceptionHandler
    private ResponseEntity<ErrorResponse> handelException(MeasurementException exception) {
        ErrorResponse response = new ErrorResponse(
                exception.getMessage(),
                System.currentTimeMillis()
        );
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }
}
