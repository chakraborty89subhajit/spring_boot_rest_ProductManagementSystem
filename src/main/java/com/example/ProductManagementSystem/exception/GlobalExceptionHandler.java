package com.example.ProductManagementSystem.exception;

import com.example.ProductManagementSystem.dto.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {
   @ExceptionHandler(NullPointerException.class)
    public ResponseEntity<?> handleNUllPointerException(Exception e ){

        ErrorResponse error = ErrorResponse.builder()
                .status(HttpStatus.INTERNAL_SERVER_ERROR.value())
                .message("you are dealing with null value")
                .build();
        return new ResponseEntity<>(error,HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(MissingServletRequestParameterException.class)
    public ResponseEntity<?> handleMissingParameterException(Exception e){
       ErrorResponse error = ErrorResponse
               .builder()
               .status(HttpStatus.BAD_REQUEST.value())
               .message(e.getMessage())
               .build();
       return new ResponseEntity<>(error,HttpStatus.BAD_REQUEST);

    }

    //exception for dto validation
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> handleMethodeArrumentNotValidException(
            MethodArgumentNotValidException ex
    ){
       List<ObjectError> allErrors= ex.getBindingResult().getAllErrors();
       Map<String,String> errors = new LinkedHashMap<>();
       allErrors.stream().forEach(
               er->{
                   String message= er.getDefaultMessage();
                   String field = ((FieldError)er).getField();
                   errors.put(field,message);
               }

       );
       return new ResponseEntity<>(errors,HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<?> handleBadRequestException(BadRequestException e) {
        ErrorResponse error = ErrorResponse.builder()
                .status(HttpStatus.BAD_REQUEST.value())
                .message(e.getMessage())
                .build();
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

}
