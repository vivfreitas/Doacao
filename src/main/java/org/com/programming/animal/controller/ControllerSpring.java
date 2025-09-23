package org.com.programming.animal.controller;


import com.fasterxml.jackson.databind.ObjectMapper;
import org.com.programming.animal.entity.AnimalEntity;
import org.com.programming.animal.entity.DTOs.AnimalDTO;
import org.com.programming.animal.entity.DTOs.AnimalDTOlist;
import org.com.programming.animal.entity.DTOs.UserDTO;
import org.com.programming.animal.entity.UserEntity;
import org.com.programming.animal.jpa.AnimalJpa;
import org.com.programming.animal.jpa.UserJpa;
import org.com.programming.animal.service.animal.AnimalService;
import org.com.programming.animal.service.cloudimg.CloudinaryService;
import org.com.programming.animal.service.user.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("api")
public class ControllerSpring {

    private final UserService userService;
    private final AnimalService animalService;
    private final CloudinaryService cloudinaryService;


    public ControllerSpring(UserService userService, AnimalService animalService, CloudinaryService cloudinaryService) {
        this.userService = userService;
        this.animalService = animalService;
        this.cloudinaryService = cloudinaryService;
    }

    /* USUÁRIO ===================================================================================== */
    @PostMapping("/createUser")
    public ResponseEntity<UserEntity> createUser(@RequestBody UserEntity userEntity){
        UserEntity obj = userService.create(userEntity);
        return ResponseEntity.ok(obj);
    }
    /* ANIMAL ====================================================================================== */
    /* @RequestBody -> Só recebe textos. Precisamos de algo que desempacote o multipart/form-data do front-end. Sendo assim, o @RequestPart.
    * Ao usar o multipart no front, as coisas vem dentro de uma caixa precisando ser desempacotada.*/
    @PostMapping("/createAnimal")
    public ResponseEntity<AnimalDTO> createAnimal(
            @RequestPart("animal") String animalJson, // 1. Recebe os dados do animal como texto JSON
            @RequestPart("arquivo") MultipartFile arquivo // 2. Recebe o arquivo da imagem
    ) throws IOException {

        // 3. Convertendo o texto JSON de volta para um objeto AnimalEntity
        ObjectMapper objectMapper = new ObjectMapper();
        AnimalEntity animalEntity = objectMapper.readValue(animalJson, AnimalEntity.class);
        String imageUrl = cloudinaryService.uploadFile(arquivo);
        animalEntity.setImgUrl(imageUrl);

        AnimalDTO objAnimal = animalService.create(animalEntity);
        return ResponseEntity.ok(objAnimal);
    }

    @PostMapping("/AllAnimal")
    public ResponseEntity<List<AnimalDTOlist>> listResponseEntityAnimal(@RequestBody AnimalEntity typeAnimal){ // Vamos pegar apenas o tipo de animal do usuário.
        List<AnimalDTOlist> animalEntities = animalService.listAllAnimal(typeAnimal.getTypeAnimal());
        return ResponseEntity.ok(animalEntities);
    }
}
