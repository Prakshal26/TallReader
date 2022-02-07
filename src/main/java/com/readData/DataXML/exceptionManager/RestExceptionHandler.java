package com.readData.DataXML.exceptionManager;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.io.FileNotFoundException;

@RestControllerAdvice
public class RestExceptionHandler {

    @ExceptionHandler(value = {InvalidQueryTypeException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse InvalidQueryType(InvalidQueryTypeException ex) {
        return new ErrorResponse(404,ex.getMessage());
    }
    @ExceptionHandler(value = {FileNotFoundException.class})
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponse fileNotFound(FileNotFoundException ex) {
        return new ErrorResponse(404,"Xml Corresponding to Query type Not Found");
    }

    @ExceptionHandler(value = {Exception.class})
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponse exception(Exception ex) {
        return new ErrorResponse(404,ex.getMessage());
    }
}
