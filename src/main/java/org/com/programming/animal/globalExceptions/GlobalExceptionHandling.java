package org.com.programming.animal.globalExceptions;

import org.springframework.http.HttpStatus;

import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.Instant;

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

    @Override
    // PRESTAR ATENÇÃO:
    // ResponseEntityExceptionHandler já possui um método para tratar
    // HttpMessageNotReadableException (ocorre quando o Spring não consegue converter o corpo da requisição (body) para o objeto Java esperado. Ex: Esperar um Enum e passar um valor VAZIO)
    // Se criarmos um @ExceptionHandler para essa mesma exception enquanto
    // estendemos essa classe, o Spring encontra múltiplos handlers possíveis
    // e lança erro de ambiguidade.
    //
    // Por isso, devemos sobrescrever (override) o método protegido
    // handleHttpMessageNotReadable(...) em vez de usar @ExceptionHandler. O Springboot vai focar apenas NESSE EXCEPTION e ignorar o que já existe.
    //
    // Observação:
    // Esse tipo de erro acontece antes do controller ser chamado,
    // pois ocorre na fase de desserialização do JSON (Jackson).
    // MAIS INFORMAÇÕES SERÁ COLOCADA NO NOTION.
    protected ResponseEntity<Object> handleHttpMessageNotReadable(
            HttpMessageNotReadableException ex,
            org.springframework.http.HttpHeaders headers,
            org.springframework.http.HttpStatusCode status,
            org.springframework.web.context.request.WebRequest request) {

        logger.warn("Enum inválido ou vazio enviado na requisição.");

        ErrorResponse error = new ErrorResponse(
                400,
                "Tipo de animal inválido ou não informado.",
                Instant.now()
        );

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }

}

