package org.com.programming.animal.controller.controllerUser;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.com.programming.animal.entity.DTOs.UserDTOsave;
import org.com.programming.animal.entity.UserEntity;
import org.com.programming.animal.service.user.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/user")
public class ControllerUser {

    private final UserService userService;

    public ControllerUser(UserService userService) {
        this.userService = userService;
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
}
