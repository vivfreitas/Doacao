package org.com.programming.animal.controller.controllerAnimal;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.com.programming.animal.entity.AnimalEntity;
import org.com.programming.animal.entity.DTOs.AnimalDTO;
import org.com.programming.animal.entity.DTOs.ListAnimalDTO;
import org.com.programming.animal.entity.DTOs.ListAnimalsDTOid;
import org.com.programming.animal.entity.DTOs.TypeAnimalDTO;
import org.com.programming.animal.service.animal.AnimalService;
import org.com.programming.animal.service.clouding.CloudinaryService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("api/animal")
public class ControllerAnimal {

    private final AnimalService animalService;
    private final CloudinaryService cloudinaryService;

    public ControllerAnimal(AnimalService animalService, CloudinaryService cloudinaryService) {
        this.animalService = animalService;
        this.cloudinaryService = cloudinaryService;
    }

    /* ANIMAL ====================================================================================== */
    @Operation(description = "Cria um animal se o usuário tiver autenticado.")
    @ApiResponses(value ={
            @ApiResponse(responseCode = "201", description = "A cada um animal criado é lançado um 201 - CREATED. É usado o multipart/form-data"),
            @ApiResponse(responseCode = "400", description = "É obrigatório o upload de uma foto do animal. Caso não coloque, é lançado o HTTP 400 - BAD_REQUEST")}
    )
    @PostMapping(value = "/createAnimal", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<AnimalDTO> createAnimal(
            @Valid
            @RequestPart("animal") String animalJson, @RequestPart("arquivo") MultipartFile multipartFile // 2. Recebe o arquivo da imagem
    ) throws IOException {

        // 3. Convertendo o texto JSON de volta para um objeto AnimalEntity
        ObjectMapper objectMapper = new ObjectMapper();
        AnimalEntity animalEntity = objectMapper.readValue(animalJson, AnimalEntity.class);
        String imageUrl = cloudinaryService.uploadFile(multipartFile);
        animalEntity.setImgUrl(imageUrl);

        AnimalDTO objAnimal = animalService.create(animalEntity);
        return ResponseEntity.status(HttpStatus.CREATED).body(objAnimal);
    }

    @Operation(description = "Vai listar todos os animais com o ID.")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Listagem de animais com ID e detalhes do Usuário.")})
    @GetMapping("listAnimals")
    public ResponseEntity<List<ListAnimalsDTOid>> listResponseEntityAnimal(){
        return ResponseEntity.ok(animalService.listAllAnimal());
    }

    @Operation(description = "Vai retornar uma lista de animais se é cachorro ou gato.")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Listagem de animais com ID e detalhes do Usuário.")})
    @PostMapping("typeOfAnimal")
    public ResponseEntity<List<ListAnimalDTO>>  typeOfAnimal( @Valid @RequestBody TypeAnimalDTO typeAnimalDTO
    ){
        return ResponseEntity.ok(animalService.listAllTypeofAnimals(typeAnimalDTO));
    }
}
