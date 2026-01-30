package org.com.programming.animal.service.animal;


import org.com.programming.animal.entity.AnimalEntity;
import org.com.programming.animal.entity.DTOs.AnimalDTO;
import org.com.programming.animal.entity.DTOs.UserDTO;
import org.com.programming.animal.entity.UserEntity;
import org.com.programming.animal.jpa.AnimalRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class AnimalService {

    private final AnimalRepository animalRepository;

    public AnimalService(AnimalRepository animalRepository){
        this.animalRepository = animalRepository;
    }

    /* CREATE ANIMAL */
    public AnimalDTO create(AnimalEntity objAnimal){

        Authentication autenticacao = SecurityContextHolder.getContext().getAuthentication();
            UserEntity usuarioLogado = (UserEntity) autenticacao.getPrincipal(); // Pegando o usuário autenticado.
            objAnimal.setUserId(usuarioLogado);
            AnimalEntity animalSaved = animalRepository.save(objAnimal);
            UserDTO usuarioDTO = new UserDTO(usuarioLogado.getIdUser(), usuarioLogado.getNameUser());

            return new AnimalDTO(
                    animalSaved.getIdAnimal(),
                    animalSaved.getNameAnimal(),
                    animalSaved.getImgUrl(),
                    usuarioDTO);
        }
    }