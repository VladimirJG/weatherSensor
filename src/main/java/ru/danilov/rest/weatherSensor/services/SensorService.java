package ru.danilov.rest.weatherSensor.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.danilov.rest.weatherSensor.exceptions.SensorNotFoundException;
import ru.danilov.rest.weatherSensor.model.Sensor;
import ru.danilov.rest.weatherSensor.repositories.SensorRepository;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class SensorService {
    private final SensorRepository sensorRepository;

    @Autowired
    public SensorService(SensorRepository sensorRepository) {
        this.sensorRepository = sensorRepository;
    }

    public List<Sensor> getAllSensors() {
        return sensorRepository.findAll();
    }

    public Sensor getSensor(int id) {
        Optional<Sensor> sensor = sensorRepository.findById(id);
        return sensor.orElseThrow(SensorNotFoundException::new);
    }

    public Optional<Sensor> getByName(String name) {
        return sensorRepository.getByName(name);
    }

    @Transactional
    public void save(Sensor sensor) {
        sensorRepository.save(sensor);
    }

    @Transactional
    public void update(Sensor upsensor, int id) {
        upsensor.setId(id);
        sensorRepository.save(upsensor);
    }

    @Transactional
    public void delete(int id) {
        sensorRepository.deleteById(id);
    }
}
