package org.com.programming.animal.controller;


import org.com.programming.animal.entity.AnimalEntity;
import org.com.programming.animal.entity.DTOs.AnimalDTO;
import org.com.programming.animal.entity.DTOs.UserDTO;
import org.com.programming.animal.entity.UserEntity;
import org.com.programming.animal.jpa.AnimalJpa;
import org.com.programming.animal.jpa.UserJpa;
import org.com.programming.animal.service.animal.AnimalService;
import org.com.programming.animal.service.user.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("api")
public class ControllerSpring {

    private final UserService userService;
    private final AnimalService animalService;
    private final UserJpa userJpa;
    private final AnimalJpa animalJpa;

    public ControllerSpring(UserService userService, AnimalService animalService, UserJpa userJpa, AnimalJpa animalJpa) {
        this.userService = userService;
        this.animalService = animalService;
        this.userJpa = userJpa;
        this.animalJpa = animalJpa;
    }

    /* USUÁRIO ===================================================================================== */
    @PostMapping("/createUser")
    public ResponseEntity<UserEntity> createUser(@RequestBody UserEntity userEntity){
        UserEntity obj = userService.create(userEntity);
        return ResponseEntity.ok(obj);
    }

    @GetMapping("/AllUser")
    public ResponseEntity<List<UserEntity>> listResponseEntityUser(){
        List<UserEntity> userEntities = userService.userEntities();
        return ResponseEntity.ok(userEntities);
    }

    /* ANIMAL ====================================================================================== */
    @PostMapping("/createAnimal")
    public ResponseEntity<AnimalDTO> createAnimal(@RequestBody AnimalEntity animalEntity){

        /* Simulação para vincular o animal. */
        UserEntity userEntity = userJpa.findByEmailUser("vivian@gmail.com"); /* Buscando o usuário por e-mail. */
        animalEntity.setUserId(userEntity);
        AnimalEntity animalSaved = animalJpa.save(animalEntity);

        UserDTO usuarioTest = new UserDTO(userEntity.getIdUser(), userEntity.getNameUser());
        AnimalDTO animalDTO = new AnimalDTO(animalSaved.getIdAnimal(), animalSaved.getNameAnimal(), usuarioTest);
        return ResponseEntity.ok(animalDTO);
    }

    @GetMapping("/AllAnimal")
    public ResponseEntity<List<AnimalEntity>> listResponseEntityAnimal(){
        List<AnimalEntity> animalEntities = animalService.listAllAnimal();
        return ResponseEntity.ok(animalEntities);
    }
}
