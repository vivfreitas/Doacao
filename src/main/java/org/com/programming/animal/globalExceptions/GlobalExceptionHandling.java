package org.com.programming.animal.globalExceptions;

import org.springframework.http.HttpStatus;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.Instant;
import java.time.LocalDate;

@ControllerAdvice
public class GlobalExceptionHandling extends ResponseEntityExceptionHandler {

    // Erros de negócio
    @ExceptionHandler(ExceptionCustomized.class)
    public ResponseEntity<ErrorResponse> handle(ExceptionCustomized ex) {
        return ResponseEntity
                .status(ex.getStatus())
                .body(new ErrorResponse(ex.getCode(), ex.getMessage(), ex.getTimestamp()));
    }

    // Erro de lógica
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleException(Exception e){
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new ErrorResponse(500, "Erro interno. Tente novamente mais tarde.", Instant.now()));
    }
}

