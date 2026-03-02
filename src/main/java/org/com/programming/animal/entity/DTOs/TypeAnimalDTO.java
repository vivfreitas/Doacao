package org.com.programming.animal.entity.DTOs;

import jakarta.validation.constraints.NotNull;
import org.com.programming.animal.entity.ENUMS.AnimalEnum;

public record TypeAnimalDTO(
        @NotNull
        AnimalEnum typeAnimal) {
}
