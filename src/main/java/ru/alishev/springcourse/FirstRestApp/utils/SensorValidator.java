package ru.alishev.springcourse.FirstRestApp.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import ru.alishev.springcourse.FirstRestApp.dto.SensorDTO;
import ru.alishev.springcourse.FirstRestApp.models.Sensor;
import ru.alishev.springcourse.FirstRestApp.services.SensorsService;


@Component
    public class SensorValidator implements Validator {

        private final SensorsService sensorsService;

        @Autowired
        public SensorValidator(SensorsService sensorsService) {
            this.sensorsService = sensorsService;
        }

        @Override
        public boolean supports(Class<?> aClass) {
            return Sensor.class.equals(aClass);
        }

        @Override
        public void validate(Object o, Errors errors) {
            SensorDTO sensorDTO = (SensorDTO) o;
            if(sensorDTO.getName()==null || sensorDTO.getName().equals("")){
                throw new EmptySensorException();
            }
            if (sensorsService.findOneByName(sensorDTO.getName()) != null) {
                errors.rejectValue("name", "", "Сенсор с таким названием уже существует в базе данных");
                throw new SensorAlreadyCreatedException();
            }
        }
    }

