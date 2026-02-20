package org.com.programming.animal.entity.DTOs;

public record ListAnimalDTO(
        String nameAnimal,String typeAnimal,
        Integer yearAnimal, String locatedAnimal,
        String imgUrl, String detailsAnimal,
        String nameUser,
        Long idUser) {
}
