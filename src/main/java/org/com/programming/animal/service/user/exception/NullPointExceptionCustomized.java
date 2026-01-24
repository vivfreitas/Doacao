package org.com.programming.animal.service.user.exception;

import org.com.programming.animal.globalExceptions.ExceptionCustomized;
import org.springframework.http.HttpStatus;

import java.time.Instant;

public class NullPointExceptionCustomized extends ExceptionCustomized {
    public NullPointExceptionCustomized() {
        super(500, "O e-mail passado é nulo", Instant.now());
    }

    @Override
    public HttpStatus getStatus() {
        return HttpStatus.INTERNAL_SERVER_ERROR;
    }
}
