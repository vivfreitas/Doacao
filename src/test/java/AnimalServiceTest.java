import org.com.programming.animal.entity.AnimalEntity;
import org.com.programming.animal.entity.DTOs.ListAnimalDTO;
import org.com.programming.animal.entity.DTOs.ListAnimalsDTOid;
import org.com.programming.animal.entity.DTOs.TypeAnimalDTO;
import org.com.programming.animal.entity.ENUMS.AnimalEnum;
import org.com.programming.animal.entity.UserEntity;
import org.com.programming.animal.jpa.AnimalRepository;
import org.com.programming.animal.service.animal.AnimalService;
import org.jspecify.annotations.NonNull;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

@ExtendWith(MockitoExtension.class)
public class AnimalServiceTest {

    @Mock
    private AnimalRepository animalRepository;

    private AnimalService animalService; // É do nosso service que puxamos a nossa lógica para ser testada.

    @BeforeEach
    void setup(){
        animalService = new AnimalService(animalRepository);
    }

    private static @NonNull List<AnimalEntity> getAnimalEntities() {
        Long animalId = 1L;
        Long userId = 1L;
        AnimalEnum animalEnum = AnimalEnum.GATO;
        List<AnimalEntity> animalList = new ArrayList<>();

        UserEntity userEntity = new UserEntity(userId, "Vivian", "vivian00gmail.com", "123", animalList);

        AnimalEntity animalEntity = new AnimalEntity(animalId, "Shokito", animalEnum, "Viralata",
                3, "Rio de Janeiro - RJ", "2199999999", "foto_img",
                "Um gato carinhoso e calmo", userEntity);
        animalList.add(animalEntity);
        return animalList;
    }

    @Test
    void deveRetornarTodosOsAnimals(){

        List<AnimalEntity> animalList = getAnimalEntities();

        Mockito.when(animalRepository.findAll()).thenReturn(animalList);// Sendo usado para encontrar o nosso animal fake
        List<ListAnimalsDTOid> listResult = animalService.listAllAnimal(); // Nossa lógica do service.

        System.out.println(listResult);
        
        Assertions.assertEquals(1, listResult.size());

        Assertions.assertEquals("Shokito", listResult.get(0).nameAnimal());
        Assertions.assertEquals("Vivian", listResult.get(0).nameUser());
    }
    
    @Test
    void deveRetornarTodosAnimalsPorTipos(){
        AnimalEnum animalEnum = AnimalEnum.GATO;
        List<AnimalEntity> animalEntities = getAnimalEntities();
        TypeAnimalDTO typeAnimalDTO = new TypeAnimalDTO(animalEnum);
        Mockito.when(animalRepository.findByTypeAnimal(typeAnimalDTO.typeAnimal())).thenReturn(animalEntities);

        List<ListAnimalDTO> listResult = animalService.listAllTypeofAnimals(typeAnimalDTO);

        System.out.println("Lista: " + listResult);

        Assertions.assertEquals(1, listResult.size()); // O tamanho da minha lista é 1. Logo: [ Animal("Shokito") ]
        Assertions.assertEquals("Shokito", listResult.get(0).nameAnimal());
        Assertions.assertEquals(animalEnum, listResult.get(0).typeAnimal()); // Por ser ENUM, não se deve colocar uma String.

    }

}
