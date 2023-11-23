package ru.alishev.springcourse.FirstRestApp.controllers;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import ru.alishev.springcourse.FirstRestApp.dto.MeasureDTO;
import ru.alishev.springcourse.FirstRestApp.models.Measure;
import ru.alishev.springcourse.FirstRestApp.models.Sensor;
import ru.alishev.springcourse.FirstRestApp.services.MeasuresService;
import ru.alishev.springcourse.FirstRestApp.services.SensorsService;
import ru.alishev.springcourse.FirstRestApp.utils.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @author Neil Alishev
 */
@RestController // @Controller + @ResponseBody над каждым методом
@RequestMapping("/measurements")
public class MeasureController {
    private final MeasuresService measuresService;
    private final SensorsService sensorsService;
    private final MeasureValidator measureValidator;
    private final ModelMapper modelMapper;

    @Autowired
    public MeasureController(MeasuresService measuresService, SensorsService sensorsService, MeasureValidator measureValidator, ModelMapper modelMapper) {
        this.measuresService = measuresService;
        this.sensorsService = sensorsService;
        this.measureValidator = measureValidator;
        this.modelMapper = modelMapper;
    }

    @GetMapping("/all")
    public List<MeasureDTO> readMeasures() {
        return measuresService.findAll().stream().map(this::convertToMeasureDTO).collect(Collectors.toList()); // проверить норм ли возвращает или надо класть в Map
    }

    @GetMapping("/rainyDaysCount")
    public Integer readRainyDaysCount() {
        return measuresService.rainyDaysCount();
    }
    @PostMapping("/add")
    public ResponseEntity<HttpStatus> createMeasure(@RequestBody @Valid MeasureDTO measureDTO, BindingResult bindingResult) {

        //по сути проверить нужно только на наличие в БД сенсора с таким названием и обработать
        //если ошибка, то обрабатываем и шлем в ответ json
        //если всё окей, то достаем из БД сенсор по имени и назначаем нашему измерению

        //валидируем
        measureValidator.validate(measureDTO, bindingResult);
        if (bindingResult.hasErrors()) {

            //вытаскиваем ошибки в читаемый вид
            StringBuilder errorMsg = new StringBuilder();
            List<FieldError> fieldErrors = bindingResult.getFieldErrors();
            for(FieldError error:fieldErrors){
                errorMsg.append(error.getField()).append(" - ").append(error.getDefaultMessage()).append(";");
            }

            //выбрасываем исключение, вместо http response и кладем в него список ошибок
             throw new MeasureIncorrectDataException(errorMsg.toString());
        }
        //если всё ок, то достаем сенсор и сохраняем измерение
        Sensor sensorFromJson = sensorsService.findOneByName(measureDTO.getName());
        measuresService.save(convertToMeasure(measureDTO), sensorFromJson);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @ExceptionHandler
    private ResponseEntity<SensorErrorResponse> handleException(SensorNotFoundException e){
        SensorErrorResponse sensorErrorResponse = new SensorErrorResponse("Сенсора с таким именем не существует");
        return new ResponseEntity<>(sensorErrorResponse, HttpStatus.CONFLICT);
    }
    @ExceptionHandler
    private ResponseEntity<SensorErrorResponse> handleException(EmptySensorException e){
        SensorErrorResponse sensorErrorResponse = new SensorErrorResponse("Нельзя добавить измерение без сенсора");
        return new ResponseEntity<>(sensorErrorResponse, HttpStatus.CONFLICT);
    }

    @ExceptionHandler
    private ResponseEntity<MeasureErrorResponse> handleException(MeasureIncorrectDataException e){
        MeasureErrorResponse measureErrorResponse = new MeasureErrorResponse("Запрос содержит некорректные данные:  " + e.getMessage());
        return new ResponseEntity<>(measureErrorResponse, HttpStatus.BAD_REQUEST);
    }
    private MeasureDTO convertToMeasureDTO(Measure measure) {
        return modelMapper.map(measure,MeasureDTO.class);
    }
    private Measure convertToMeasure(MeasureDTO measureDTO) {
        return modelMapper.map(measureDTO, Measure.class);
    }

    @PostMapping("/test")
    public ResponseEntity<HttpStatus> addTestMeasure(@RequestBody @Valid MeasureDTO measureDTO, BindingResult bindingResult) {
        System.out.println("IN TEST METHOD");
        System.out.println("IN TEST METHOD");
        System.out.println("IN TEST METHOD");
        System.out.println();

        //по сути проверить нужно только на наличие в БД сенсора с таким названием и обработать
        //если ошибка, то обрабатываем и шлем в ответ json
        //если всё окей, то достаем из БД сенсор по имени и назначаем нашему измерению

        //валидируем
        measureValidator.validate(measureDTO, bindingResult);

        //если всё ок, то достаем сенсор и сохраняем измерение
        Sensor sensorFromJson = sensorsService.findOneByName(measureDTO.getName());
        measuresService.save(convertToMeasure(measureDTO), sensorFromJson);


        //и сохраняем в БД - нужно просто понять, как обрабатывать ошибки

        System.out.println(measureDTO.getValue());
        System.out.println(measureDTO.getName());
        System.out.println(measureDTO.isRaining());
        System.out.println();

        Measure measure = convertToMeasure(measureDTO);
        System.out.println(measure.getValue());
        System.out.println(measure.getSensor().getName());
        System.out.println(measure.isRaining());
        System.out.println();



//        Sensor sensor = sensorsService.findOneByName("PabloJava");
//        measureDTO.setSensor(sensor);
//        measureDTO.setValue(26);
//        measureDTO.setRaining(false);
//        measuresService.save(convertToMeasure(measureDTO));

        System.out.println();
        System.out.println("AFTER TEST METHOD");
        System.out.println("AFTER TEST METHOD");
        System.out.println("AFTER TEST METHOD");
        return ResponseEntity.ok(HttpStatus.OK);
    }
}
