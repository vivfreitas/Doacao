package org.com.programming.animal.service.animal;


import org.com.programming.animal.entity.AnimalEntity;
import org.com.programming.animal.entity.DTOs.AnimalDTO;
import org.com.programming.animal.entity.DTOs.AnimalDTOlist;
import org.com.programming.animal.entity.DTOs.UserDTO;
import org.com.programming.animal.entity.UserEntity;
import org.com.programming.animal.jpa.AnimalJpa;
import org.com.programming.animal.jpa.UserJpa;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class AnimalService {

    private final AnimalJpa animalJpa;

    public AnimalService(AnimalJpa animalJpa){
        this.animalJpa = animalJpa;
    }

    /* READ ALL - Se o animal for Gato, ele retorá apenas gato. Se for cachorro, será cachorro.*/
    public List<AnimalDTOlist> listAllAnimal(String animalType){
        List<AnimalDTOlist> animal = new ArrayList<>(); // Vai voltar apenas Gato
        List<AnimalEntity> animalEntities = animalJpa.findAll();
        for (AnimalEntity obj : animalEntities){
            if (obj.getTypeAnimal().equals(animalType)){;
                animal.add(new AnimalDTOlist(obj.getNameAnimal()));
            }
        }
        return animal;
        }

    /* CREATE ANIMAL */
    public AnimalDTO create(AnimalEntity objAnimal){
        Authentication autenticacao = SecurityContextHolder.getContext().getAuthentication();
        UserEntity usuarioLogado = (UserEntity) autenticacao.getPrincipal(); // Pegando o usuário autenticado.
        objAnimal.setUserId(usuarioLogado);

        AnimalEntity animalSaved = animalJpa.save(objAnimal);
        UserDTO usuarioDTO = new UserDTO(usuarioLogado.getIdUser(), usuarioLogado.getNameUser());

        return new AnimalDTO(
                animalSaved.getIdAnimal(),
                animalSaved.getNameAnimal(),
                animalSaved.getImgUrl(),
                usuarioDTO
        );
    }



}
