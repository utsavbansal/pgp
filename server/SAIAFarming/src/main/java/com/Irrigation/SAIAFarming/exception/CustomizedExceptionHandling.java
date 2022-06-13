package com.Irrigation.SAIAFarming.exception;

import com.Irrigation.SAIAFarming.model.ResponseStatus;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;
import java.util.UUID;

@RestControllerAdvice
public class CustomizedExceptionHandling extends ResponseEntityExceptionHandler {


    @RequestMapping("/error")
    @ExceptionHandler(CustomApplicationException.class)
    public ResponseEntity<Object> handleExceptions(CustomApplicationException exception, WebRequest webRequest) {
        System.out.println("Error : "+exception);
        System.out.println("Request : "+webRequest);
        //ExceptionResponse response = new ExceptionResponse();
        ResponseStatus responseStatus = new ResponseStatus();
        responseStatus.setReqId(UUID.randomUUID().toString());
        responseStatus.setHttpCode(exception.getHttpStatus());
        responseStatus.setStatusCode(exception.getMessage().substring(0,4));
        responseStatus.setMsg(exception.getMessage());
        ResponseEntity<Object> entity = new ResponseEntity<>(responseStatus, exception.getHttpStatus());
        return entity;
    }


}