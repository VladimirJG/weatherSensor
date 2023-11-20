package ru.danilov.rest.weatherSensor.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.danilov.rest.weatherSensor.model.Sensor;

@Repository
public interface SensorRepository extends JpaRepository<Sensor, Integer> {
}
