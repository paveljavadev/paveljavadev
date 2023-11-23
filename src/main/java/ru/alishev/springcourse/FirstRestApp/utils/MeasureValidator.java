package ru.alishev.springcourse.FirstRestApp.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import ru.alishev.springcourse.FirstRestApp.dto.MeasureDTO;
import ru.alishev.springcourse.FirstRestApp.models.Measure;
import ru.alishev.springcourse.FirstRestApp.models.Sensor;
import ru.alishev.springcourse.FirstRestApp.services.MeasuresService;
import ru.alishev.springcourse.FirstRestApp.services.SensorsService;


@Component
public class MeasureValidator implements Validator {

    private final MeasuresService measuresService;
    private final SensorsService sensorsService;

    @Autowired
    public MeasureValidator(MeasuresService measuresService, SensorsService sensorsService) {
        this.measuresService = measuresService;
        this.sensorsService = sensorsService;
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return Measure.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        MeasureDTO measureDTO = (MeasureDTO) o;

        if(measureDTO.getName()==null){
            throw new EmptySensorException();
        }

        if(sensorsService.findOneByName(measureDTO.getName())==null) {
            errors.rejectValue("name", "", "Сенсор с таким названием не существует в базе данных");
            throw new SensorNotFoundException();

        }


    }
}


