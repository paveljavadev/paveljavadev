package ru.alishev.springcourse.FirstRestApp.utils;

public class MeasureIncorrectDataException extends RuntimeException{
    public MeasureIncorrectDataException(String msg){
        super(msg);
    }
}
