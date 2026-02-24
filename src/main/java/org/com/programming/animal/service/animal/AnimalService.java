package org.com.programming.animal.service.animal;

import org.com.programming.animal.entity.AnimalEntity;
import org.com.programming.animal.entity.DTOs.AnimalDTO;
import org.com.programming.animal.entity.DTOs.ListAnimalDTO;
import org.com.programming.animal.entity.DTOs.UserDTO;
import org.com.programming.animal.entity.UserEntity;
import org.com.programming.animal.jpa.AnimalRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@Service
public class AnimalService {

    private final AnimalRepository animalRepository;
    private static final Logger logger = LoggerFactory.getLogger(AnimalService.class);

    public AnimalService(AnimalRepository animalRepository){
        this.animalRepository = animalRepository;
    }

    // Criando Animal
    public AnimalDTO create(AnimalEntity objAnimal){
        Authentication authUser = SecurityContextHolder.getContext().getAuthentication();
        UserEntity authTrue = (UserEntity) authUser.getPrincipal();
        objAnimal.setUserId(authTrue);
        AnimalEntity animalSaved = animalRepository.save(objAnimal);
        UserDTO userDTO = new UserDTO(authTrue.getIdUser(), authTrue.getNameUser());

        return new AnimalDTO(
                animalSaved.getIdAnimal(),
                animalSaved.getNameAnimal(),
                animalSaved.getTypeAnimal(),
                animalSaved.getBreedAnimal(),
                animalSaved.getYearAnimal(),
                animalSaved.getLocatedAnimal(),
                animalSaved.getContactAnimal(),
                animalSaved.getDetailsAnimal(),
                animalSaved.getImgUrl(),
                userDTO);
        }


    // Logs
    // List all animal - Do not necessary auth.
    public List<ListAnimalDTO> listAllAnimal(){
        List<ListAnimalDTO> newAnimals = new ArrayList<>();
        List<AnimalEntity> allAnimals = animalRepository.findAll();

        for (AnimalEntity animal : allAnimals){
            newAnimals.add(new ListAnimalDTO(
                    animal.getNameAnimal(),
                    animal.getTypeAnimal(),
                    animal.getBreedAnimal(),
                    animal.getYearAnimal(),
                    animal.getLocatedAnimal(),
                    animal.getContactAnimal(),
                    animal.getDetailsAnimal(),
                    animal.getImgUrl(),
                    animal.getUserId().getNameUser(),
                    animal.getUserId().getIdUser()));
        }
        logger.info("Get all animals successfully");
        return newAnimals;
    }

    // Criar lista para pegar apenas gato.
    // Criar lista para pegar apenas cachorro.

}
