package org.com.programming.animal.globalExceptions.exception;

import org.com.programming.animal.globalExceptions.ExceptionCustomized;
import org.springframework.http.HttpStatus;

import java.time.Instant;

public class EmailExistException extends ExceptionCustomized {
    public EmailExistException() {
        super(409, "E-mail já cadastrado. Tente novamente!", Instant.now());
    }

    @Override
    public HttpStatus getStatus() {
        return HttpStatus.CONFLICT;
    }
}
