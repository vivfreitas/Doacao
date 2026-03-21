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
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.test.context.support.WithMockUser;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;

@ExtendWith(MockitoExtension.class)
public class AnimalServiceTest {

    @Mock
    private AnimalRepository animalRepository;

    private AnimalService animalService; // É do nosso service que puxamos a nossa lógica para ser testada.

    @BeforeEach
    void setup(){
        animalService = new AnimalService(animalRepository);
    }

    // Criando uma lista de animais e retornando.
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

    // Criando um animal e retornando o memo.
    private static @NonNull AnimalEntity getAnimal(){
        Long animalId = 1L;
        Long userId = 1L;
        AnimalEnum animalEnum = AnimalEnum.GATO;
        List<AnimalEntity> animalList = new ArrayList<>();

        UserEntity userEntity = new UserEntity(userId, "Vivian", "vivian00gmail.com", "123", animalList);

        return new AnimalEntity(animalId, "Shokito", animalEnum, "Viralata",
                3, "Rio de Janeiro - RJ", "2199999999", "foto_img",
                "Um gato carinhoso e calmo", userEntity);
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

    @Test
    void deveCriarUmAnimal(){
        // Vamos criar o nosso usuário e animal.
        AnimalEntity animalEntity = getAnimal();
        UserEntity userEntity = animalEntity.getUserId();

        // Simular o Security Context
        Authentication auth = Mockito.mock(Authentication.class);
        SecurityContext securityContext = Mockito.mock(SecurityContext.class);
        Mockito.when(securityContext.getAuthentication()).thenReturn(auth);
        Mockito.when(auth.getPrincipal()).thenReturn(userEntity);
        SecurityContextHolder.setContext(securityContext);

        Mockito.when(animalRepository.save(any())).thenReturn(animalEntity);

        System.out.println(animalEntity.getNameAnimal());
        animalService.create(animalEntity);

        // Se tivermos muitos AssertionsEquals, é bom que usamos dessa forma para que nos dê o problema de uma vez só
        Assertions.assertAll("Verificando atributos do nosso animal",
                () -> Assertions.assertEquals("Shokito", animalEntity.getNameAnimal()),
                () -> Assertions.assertEquals(AnimalEnum.GATO, animalEntity.getTypeAnimal()),
                () -> Assertions.assertNotNull(animalEntity.getIdAnimal())
        );
    }
}
