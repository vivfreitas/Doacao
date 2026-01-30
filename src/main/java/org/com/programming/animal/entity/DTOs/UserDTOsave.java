package org.com.programming.animal.entity.DTOs;

import jakarta.validation.constraints.NotBlank;

public record UserDTOsave(
        @NotBlank(message = "É necessário passar o nome do usuário.")
        String userName,
        @NotBlank(message = "É necessário passar o e-mail do usuário.")
        String userEmail,
        @NotBlank(message = "É necessário passar a senha do usuário.")
        String userPassword) {
}
