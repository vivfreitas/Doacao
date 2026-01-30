package org.com.programming.animal.entity.DTOs;

import jakarta.validation.constraints.NotBlank;

public record AuthRequest(
        @NotBlank(message = "É preciso passar o e-mail do usuário.")
        String email,
        @NotBlank(message = "A senha não pode estar em branco.")
        String senha){
}
