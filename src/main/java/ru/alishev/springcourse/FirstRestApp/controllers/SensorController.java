package ru.alishev.springcourse.FirstRestApp.controllers;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.alishev.springcourse.FirstRestApp.dto.SensorDTO;
import ru.alishev.springcourse.FirstRestApp.models.Sensor;
import ru.alishev.springcourse.FirstRestApp.services.SensorsService;
import ru.alishev.springcourse.FirstRestApp.utils.*;

import javax.validation.Valid;
import java.net.http.HttpResponse;

/**
 * @author Neil Alishev
 */
@RestController // @Controller + @ResponseBody над каждым методом
@RequestMapping("/sensors")
public class SensorController {
    private final SensorValidator sensorValidator;

    private final SensorsService sensorsService;
    private final ModelMapper modelMapper;

    @Autowired
    public SensorController(SensorValidator sensorValidator, SensorsService sensorsService, ModelMapper modelMapper) {
        this.sensorValidator = sensorValidator;
        this.sensorsService = sensorsService;
        this.modelMapper = modelMapper;
    }


    @PostMapping("/registration")
    public ResponseEntity<HttpStatus> createSensor(@RequestBody @Valid SensorDTO sensorDto, BindingResult bindingResult) {
        sensorValidator.validate(sensorDto, bindingResult);
        if (bindingResult.hasErrors()) {
            return ResponseEntity.ok(HttpStatus.CONFLICT);
        }
        sensorsService.save(convertToSensor(sensorDto));
        return ResponseEntity.ok(HttpStatus.OK);
    }

    private Sensor convertToSensor(SensorDTO sensorDto) {
        return modelMapper.map(sensorDto,Sensor.class);
    }

    @ExceptionHandler
    private ResponseEntity<SensorErrorResponse> handleException(SensorAlreadyCreatedException e){
           SensorErrorResponse sensorErrorResponse = new SensorErrorResponse("Сенсор с таким именем уже существует");
           return new ResponseEntity<>(sensorErrorResponse, HttpStatus.CONFLICT);
    }
    @ExceptionHandler
    private ResponseEntity<SensorErrorResponse> handleException(EmptySensorException e){
        SensorErrorResponse sensorErrorResponse = new SensorErrorResponse("Нельзя добавить сенсор с пустым названием");
        return new ResponseEntity<>(sensorErrorResponse, HttpStatus.CONFLICT);
    }
}
