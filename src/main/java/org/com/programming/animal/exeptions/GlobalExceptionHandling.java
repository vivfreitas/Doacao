package org.com.programming.animal.exeptions;

import org.springframework.http.HttpStatus;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandling extends ResponseEntityExceptionHandler {

    @ExceptionHandler(ExceptionCustomized.class)
    public final ResponseEntity<Object> exceptionHandler(Exception exception){
        int statusCode = 0;

        /* É bom verificar como a exceção interna está chegando para o usuário. */
        if (exception instanceof HttpExceptionCustomized){
            statusCode = HttpStatus.FORBIDDEN.value();
        }
        return new ResponseEntity<>(new ErrorResponse(
                ((ExceptionCustomized) exception).getCode(),
                exception.getMessage()),
                HttpStatus.valueOf(statusCode));
    }
}

