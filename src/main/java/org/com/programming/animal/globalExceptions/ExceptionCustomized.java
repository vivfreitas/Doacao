package org.com.programming.animal.globalExceptions;


import org.springframework.http.HttpStatus;

import java.time.Instant;

// Colocar como abstrato força o usuário a herdar
public abstract class ExceptionCustomized extends RuntimeException {

    private final Integer code;
    private final Instant timestamp;

    protected ExceptionCustomized(Integer code, String message, Instant timestamp) {
        super(message); // Chamando o RuntimeException(String message) pq ele já tem uma lógica dentro dele para armazenar a mensagem.
        this.code = code;
        this.timestamp = timestamp;
    }

    public abstract HttpStatus getStatus();

    public Integer getCode() {
        return code;
    }

    public Instant getTimestamp() {
        return timestamp;
    }
}

