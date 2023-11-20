package ru.danilov.rest.weatherSensor.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.danilov.rest.weatherSensor.model.Measurement;
import ru.danilov.rest.weatherSensor.repositories.MeasurementRepository;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class MeasurementService {
    private final MeasurementRepository measurementRepository;

    @Autowired
    public MeasurementService(MeasurementRepository measurementRepository) {
        this.measurementRepository = measurementRepository;
    }

    public List<Measurement> getAllMeasurements() {
        return measurementRepository.findAll();
    }

    public Optional<Measurement> getMeasurementById(int id) {
        return measurementRepository.findById(id);
    }

    @Transactional
    public void saveMeasurement(Measurement measurement) {
        measurementRepository.save(measurement);
    }

    @Transactional
    public void updateMeasurement(Measurement upMeasurement, int id) {
        Optional<Measurement> measurement = measurementRepository.findById(id);

        upMeasurement.setSensorOwner(measurement.get().getSensorOwner());
        upMeasurement.setId(measurement.get().getId());
        measurementRepository.save(upMeasurement);
    }

    @Transactional
    public void deleteMeasurement(int id) {
        measurementRepository.deleteById(id);
    }
}