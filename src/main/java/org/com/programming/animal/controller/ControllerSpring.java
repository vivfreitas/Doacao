package org.com.programming.animal.controller;


import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.validation.constraints.NotNull;
import org.com.programming.animal.entity.AnimalEntity;
import org.com.programming.animal.entity.DTOs.AnimalDTO;

import org.com.programming.animal.entity.DTOs.UserEmailDTO;
import org.com.programming.animal.entity.UserEntity;
import org.com.programming.animal.jpa.UserRepository;
import org.com.programming.animal.service.animal.AnimalService;
import org.com.programming.animal.service.clouding.CloudinaryService;
import org.com.programming.animal.service.user.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    private final UserRepository userRepository;

    public ControllerSpring(UserService userService, AnimalService animalService, CloudinaryService cloudinaryService, UserRepository userRepository) {
        this.userService = userService;
        this.animalService = animalService;
        this.cloudinaryService = cloudinaryService;
        this.userRepository = userRepository;
    }

    /* USUÁRIO ===================================================================================== */
    @PostMapping("/createUser")
    public ResponseEntity<Object> createUser(@RequestBody UserEntity userEntity){
        if (userRepository.existsByEmailUser(userEntity.getEmailUser())){
            return ResponseEntity.status(HttpStatus.CONFLICT).body("E-mail já cadastrado");
        }
        UserEntity obj = userService.create(userEntity);
        return ResponseEntity.status(HttpStatus.CREATED).body("Usuário criado!");
    }


    // TESTANDO EXCEPTION
    @GetMapping("listas")
    public ResponseEntity<List<UserEntity>> listAll(){
        return ResponseEntity.ok(userService.userEntities());
    }

    @PostMapping("emailTeste")
    public ResponseEntity<String> testeException(@RequestBody UserEmailDTO emailObj){
        String obj = userService.retornarNomeUsuario(emailObj.email());
        return ResponseEntity.ok(obj);
    }

    /* ANIMAL ====================================================================================== */
    /* @RequestBody -> Só recebe textos. Precisamos de algo que desempacote o multipart/form-data do front-end. Sendo assim, o @RequestPart.
    * Ao usar o multipart no front, as coisas vem dentro de uma caixa precisando ser desempacotada.*/
    @PostMapping("/createAnimal")
    public ResponseEntity<AnimalDTO> createAnimal(
            @RequestPart("animal") String animalJson, // 1. Recebe os dados do animal como texto JSON
            @RequestPart("arquivo") MultipartFile multipartFile // 2. Recebe o arquivo da imagem
    ) throws IOException {

        // 3. Convertendo o texto JSON de volta para um objeto AnimalEntity
        ObjectMapper objectMapper = new ObjectMapper();
        AnimalEntity animalEntity = objectMapper.readValue(animalJson, AnimalEntity.class);
        String imageUrl = cloudinaryService.uploadFile(multipartFile);
        animalEntity.setImgUrl(imageUrl);

        AnimalDTO objAnimal = animalService.create(animalEntity);
        return ResponseEntity.ok(objAnimal);
    }

}
