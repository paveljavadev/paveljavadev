package ru.alishev.springcourse.FirstRestApp.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.alishev.springcourse.FirstRestApp.models.Sensor;
import ru.alishev.springcourse.FirstRestApp.repositories.SensorsRepository;
import ru.alishev.springcourse.FirstRestApp.utils.SensorNotFoundException;

import java.util.List;
import java.util.Optional;

/**
 * @author Neil Alishev
 */
@Service
@Transactional(readOnly = true)
public class SensorsService {

    private final SensorsRepository sensorsRepository;

    @Autowired
    public SensorsService(SensorsRepository sensorsRepository) {
        this.sensorsRepository = sensorsRepository;

    }

    //добавляем новый сенсор в БД
    @Transactional
    public void save (Sensor sensor){
        sensorsRepository.save(sensor);
    }

    //ищем по имени
    public Sensor findOneByName(String name) {

        Sensor sensor = sensorsRepository.findSensorByName(name);
        //Optional<Sensor> sensor = Optional.ofNullable(sensorsRepository.findSensorByName(name));
        //return sensor.orElseThrow(SensorNotFoundException::new);
        return sensor;
    }


}
