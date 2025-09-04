package org.com.programming.animal.service.animal;


import org.com.programming.animal.entity.AnimalEntity;
import org.com.programming.animal.entity.DTOs.AnimalDTO;
import org.com.programming.animal.entity.DTOs.UserDTO;
import org.com.programming.animal.entity.UserEntity;
import org.com.programming.animal.jpa.AnimalJpa;
import org.com.programming.animal.jpa.UserJpa;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AnimalService {

    private final AnimalJpa animalJpa;
    private final UserJpa userJpa;


    public AnimalService(AnimalJpa animalJpa, UserJpa userJpa){
        this.animalJpa = animalJpa;
        this.userJpa = userJpa;
    }

    /* READ ALL */
    public List<AnimalEntity> listAllAnimal(){
        return animalJpa.findAll();
    }

    /* CREATE ANIMAL */
    public AnimalEntity create(AnimalEntity objAnimal){
        /* Simulação para vincular o animal. */
        UserEntity userEntity = userJpa.findByEmailUser("vivian@gmail.com"); /* Buscando o usuário por e-mail. */
        objAnimal.setUserId(userEntity);

        return animalJpa.save(objAnimal);
    }


}
