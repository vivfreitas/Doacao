package org.com.programming.animal.entity.DTOs;

import jakarta.validation.constraints.NotNull;
import org.com.programming.animal.entity.ENUMS.AnimalEnum;

public record AnimalDTO(
        Long idAnimal,
        @NotNull(message = "Nome do animal")
        String nameAnimal,
        AnimalEnum typeAnimal,
        String breedAnimal,
        Integer yearAnimal,
        String locatedAnimal,
        String contactAnimal,
        String detailsAnimal,
        @NotNull(message = "Arquivo de foto do animal")
        String imgUrl,
        UserDTO userDTO ) {
}
