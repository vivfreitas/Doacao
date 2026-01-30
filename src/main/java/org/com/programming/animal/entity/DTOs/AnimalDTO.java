package org.com.programming.animal.entity.DTOs;

import jakarta.validation.constraints.NotNull;

public record AnimalDTO(
        Long idAnimal,
        @NotNull(message = "Nome do animal")
        String nameAnimal,
        @NotNull(message = "Arquivo de foto do animal")
        String imgUrl,
        UserDTO userDTO ) {
}
