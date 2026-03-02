package org.com.programming.animal.globalExceptions.exception;

import org.com.programming.animal.globalExceptions.ExceptionCustomized;
import org.springframework.http.HttpStatus;

import java.time.Instant;

public class EmailNotFoundException extends ExceptionCustomized {
    public EmailNotFoundException(String message) {
        super(404, "O e-mail " + message + " não foi localizado.", Instant.now());
    }

    @Override
    public HttpStatus getStatus() {
        return HttpStatus.NOT_FOUND;
    }
}
