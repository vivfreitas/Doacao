package org.com.programming.animal.service.user.exception;

import org.com.programming.animal.globalExceptions.HttpExceptionCustomized;

public class UsuarioNaoEncontradoException extends HttpExceptionCustomized {
    public UsuarioNaoEncontradoException(String code) {
        super("001", String.format("Usuário " + code + " não encontrado."));
    }
}
