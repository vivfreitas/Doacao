package org.com.programming.animal.service.animal;

import org.com.programming.animal.entity.AnimalEntity;
import org.com.programming.animal.entity.DTOs.*;
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


    // VAMOS LISTAR TODOS OS ANIMAIS. SERÁ IMPORTANTE PARA O FRONT-END.
    public List<ListAnimalsDTOid> listAllAnimal(){
        List<ListAnimalsDTOid> newAnimals = new ArrayList<>();
        List<AnimalEntity> allAnimals = animalRepository.findAll();

        for (AnimalEntity animal : allAnimals){
            newAnimals.add(new ListAnimalsDTOid(
                    animal.getIdAnimal(),
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

    // Criar lista para pegar apenas Gatos OU Cachorros - FILTRO USADO PARA O FRONT-END.
    public List<ListAnimalDTO> listAllTypeofAnimals(TypeAnimalDTO typeAnimalDTO){

        List<ListAnimalDTO> listAllAnimals = new ArrayList<>();

        List<AnimalEntity> obj = animalRepository.findByTypeAnimal(typeAnimalDTO.typeAnimal());
        for (AnimalEntity objList : obj){
            System.out.println(objList.getNameAnimal());
            listAllAnimals.add(new ListAnimalDTO(
                    objList.getNameAnimal(),
                    objList.getTypeAnimal(),
                    objList.getBreedAnimal(),
                    objList.getYearAnimal(),
                    objList.getLocatedAnimal(),
                    objList.getContactAnimal(),
                    objList.getDetailsAnimal(),
                    objList.getImgUrl(),
                    objList.getUserId().getNameUser(),
                    objList.getUserId().getIdUser()));
        }
        logger.info("Foi retornado uma lista de animaiis do tipo {}", typeAnimalDTO.typeAnimal());
        return listAllAnimals;
    }
}
