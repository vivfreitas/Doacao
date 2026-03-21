package org.com.programming.animal.globalExceptions.exception;

import org.com.programming.animal.globalExceptions.ExceptionCustomized;
import org.springframework.http.HttpStatus;

import java.time.Instant;

// RETIRAR. SEM USO!
public class NotAuthException extends ExceptionCustomized {
    public NotAuthException() {
        super(401, "Usuário não autenticado", Instant.now());
    }

    @Override
    public HttpStatus getStatus() {
        return HttpStatus.UNAUTHORIZED;
    }
}
