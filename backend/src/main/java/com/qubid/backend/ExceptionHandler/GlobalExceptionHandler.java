package com.qubid.backend.ExceptionHandler;

import  com.qubid.backend.Response.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
//import com.qubid.backend.Response.ErrorResponse;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String,String> handleMethodArgumentNotValidException(MethodArgumentNotValidException e){
        Map<String,String> errorMap=new HashMap<>();
        System.out.println(e.getBindingResult());
        e.getBindingResult().getAllErrors().forEach(error->{
            String fieldName=((FieldError)error).getField();
            String msg=error.getDefaultMessage();
            errorMap.put(fieldName,msg);
        });
        return errorMap;
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleException(Exception e){
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(new ErrorResponse(e.getMessage(),400));
    }
}
