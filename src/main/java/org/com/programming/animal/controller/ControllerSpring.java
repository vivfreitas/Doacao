package org.com.programming.animal.controller;


import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.com.programming.animal.entity.AnimalEntity;
import org.com.programming.animal.entity.DTOs.AnimalDTO;

import org.com.programming.animal.entity.DTOs.ListAnimalDTO;
import org.com.programming.animal.entity.DTOs.UserDTOsave;
import org.com.programming.animal.entity.UserEntity;
import org.com.programming.animal.jpa.UserRepository;
import org.com.programming.animal.service.animal.AnimalService;
import org.com.programming.animal.service.clouding.CloudinaryService;
import org.com.programming.animal.service.user.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
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

    public ControllerSpring(UserService userService, AnimalService animalService, CloudinaryService cloudinaryService) {
        this.userService = userService;
        this.animalService = animalService;
        this.cloudinaryService = cloudinaryService;
    }

    /* USUÁRIO ===================================================================================== */
    @Operation(description = "Cria um novo usuário.")
    @ApiResponses(value ={
            @ApiResponse(responseCode = "201", description = "Cadastra um novo usuário no banco de dados."),
            @ApiResponse(responseCode = "409", description = "Caso o e-mail já exista, é retornando um 409 - CONFLIT.")}
    )
    @PostMapping("/createUser")
    public ResponseEntity<Object> createUser(@Valid @RequestBody UserDTOsave userEntity){
        UserEntity obj = userService.create(userEntity);
        return ResponseEntity.status(HttpStatus.CREATED).body("Usuário criado!");
    }

    /* ANIMAL ====================================================================================== */
    /* @RequestBody -> Só recebe textos. Precisamos de algo que desempacote o multipart/form-data do front-end. Sendo assim, o @RequestPart.
    * Ao usar o multipart no front, as coisas vem dentro de uma caixa precisando ser desempacotada.*/
    @Operation(description = "Cria um animal se o usuário tiver autenticado.")
    @ApiResponses(value ={
            @ApiResponse(responseCode = "201", description = "A cada um animal criado é lançado um 201 - CREATED. É usado o multipart/form-data"),
            @ApiResponse(responseCode = "400", description = "É obrigatório o upload de uma foto do animal. Caso não coloque, é lançado o HTTP 400 - BAD_REQUEST")}
    )
    @PostMapping(value = "/createAnimal", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<AnimalDTO> createAnimal(
            @Valid
            @RequestPart("animal") String animalJson, // 1. Recebe os dados do animal como texto JSON
            @RequestPart("arquivo") MultipartFile multipartFile // 2. Recebe o arquivo da imagem
    ) throws IOException {

        // 3. Convertendo o texto JSON de volta para um objeto AnimalEntity
        ObjectMapper objectMapper = new ObjectMapper();
        AnimalEntity animalEntity = objectMapper.readValue(animalJson, AnimalEntity.class);
        String imageUrl = cloudinaryService.uploadFile(multipartFile);
        animalEntity.setImgUrl(imageUrl);

        AnimalDTO objAnimal = animalService.create(animalEntity);
        return ResponseEntity.status(HttpStatus.CREATED).body(objAnimal);
    }

    // List all animal - Do not necessary auth.
    @GetMapping("listAnimals")
    public ResponseEntity<List<ListAnimalDTO>> listResponseEntityAnimal(){
        return ResponseEntity.ok(animalService.listAllAnimal());
    }

}
