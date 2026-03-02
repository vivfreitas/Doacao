package org.com.programming.animal.entity.DTOs;

import org.com.programming.animal.entity.ENUMS.AnimalEnum;

public record ListAnimalsDTOid(
        Long idAnimal,
        String nameAnimal,
        AnimalEnum typeAnimal,
        String breedAnimal,
        Integer yearAnimal,
        String locatedAnimal,
        String contactAnimal,
        String detailsAnimal,
        String imgUrl,
        String nameUser,
        Long idUser
){}

