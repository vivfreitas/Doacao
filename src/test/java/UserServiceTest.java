import org.com.programming.animal.entity.AnimalEntity;
import org.com.programming.animal.entity.DTOs.UserDTOsave;
import org.com.programming.animal.entity.UserEntity;
import org.com.programming.animal.infra.jwt.TokenService;
import org.com.programming.animal.jpa.UserRepository;
import org.com.programming.animal.service.user.UserService;
import org.com.programming.animal.service.user.userDetails.UserDetailsServiceAuth;
import org.jspecify.annotations.NonNull;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @Mock
    private UserRepository userRepository;
    @Mock
    private PasswordEncoder passwordEncoder;
    @Mock
    private AuthenticationManager authenticationManager;
    @Mock
    private UserDetailsServiceAuth userDetailsServiceAuth;
    @Mock
    private TokenService tokenService;

    private UserService userService;

    @BeforeEach
    void setup(){
        userService = new UserService(userRepository, passwordEncoder, authenticationManager, userDetailsServiceAuth, tokenService);
    }

    private static @NonNull UserDTOsave criarUsuario(){
        String userPassword = "naoentre";

        return new UserDTOsave("Vivian", "vivian@gmail.com", userPassword);
    }
    private  static @NonNull UserEntity userEntity(){
        UserDTOsave user = criarUsuario();
        Long idUser = 1L;
        List<AnimalEntity> list = new ArrayList<>();
        return new UserEntity(idUser, user.userName(), user.userEmail(), user.userPassword(), list);
    }

    @Test
    void deveCriarUmUsuario(){
        UserDTOsave user = criarUsuario();
        UserEntity userEntity = userEntity();

        Mockito.when(userRepository.save(any(UserEntity.class))).thenReturn(userEntity);

        Mockito.when(passwordEncoder.encode(any())).thenReturn("senha_cripto");
        System.out.println(userEntity.getNameUser());
        userService.create(user);

        Assertions.assertAll("Verificando tudo o UserEntity",
                () -> Assertions.assertEquals("Vivian", userEntity.getNameUser()),
                () -> Assertions.assertEquals("vivian@gmail.com", userEntity.getEmailUser()),
                () -> Assertions.assertNotNull(userEntity.getIdUser()));
    }
}
