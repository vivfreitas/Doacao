package org.com.programming.animal.infra;

import org.com.programming.animal.infra.jwt.AuthToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Configuration
@EnableWebSecurity
public class SpringSecurityConfig {

    /* Será o nosso provedor para verificar o nosso usuário e senha, de acordo com o que configuramos no nosso UserDetailsConfig. */
    private final AuthenticationProvider authenticationProvider;
    private final AuthToken authToken;

    @Autowired
    public SpringSecurityConfig(AuthenticationProvider authenticationProvider, AuthToken authToken){
        /* Por enquanto não iremos implementa-lo. */
        this.authenticationProvider = authenticationProvider;
        this.authToken = authToken;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception{
        return httpSecurity
                .cors(cors -> cors.configurationSource(corsConfigurationClass()))
                .csrf(AbstractHttpConfigurer:: disable)
                .authorizeHttpRequests(auth ->{
                    // Rotas do Swagger e OpenAPI
                    auth.requestMatchers("/swagger-ui/**").permitAll();
                    auth.requestMatchers("/v3/api-docs/**").permitAll();
                    auth.requestMatchers("/swagger-resources/**").permitAll();
                    auth.requestMatchers("/webjars/**").permitAll();

                    auth.requestMatchers("/api/createUser").permitAll();
                    auth.requestMatchers("/api/listAnimals").permitAll();
                    auth.requestMatchers("/api/createAnimal").permitAll();
                    auth.requestMatchers("/auth/**").permitAll();
                    auth.anyRequest().authenticated();
                })
                .sessionManagement( session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)) /* Não mantemos login na nossa API, ou seja, STATELESS. */
                .authenticationProvider(authenticationProvider)
                /* Vai rodar o nosso filtro do JWT antes do filtro padrão do SpringSecurity */
                .addFilterBefore(authToken, UsernamePasswordAuthenticationFilter.class)
                .build();

    }

    /* Configuração do CORs */
    @Bean
    public CorsConfigurationSource corsConfigurationClass(){
        CorsConfiguration corsConfiguration = new CorsConfiguration();

        corsConfiguration.addAllowedOrigin("http://127.0.0.1:5500");
        corsConfiguration.addAllowedOrigin("http://localhost:5500"); // Adicione esta linha
        corsConfiguration.setAllowCredentials(false); // Permite o uso de tokens
        corsConfiguration.addAllowedMethod("*");
        corsConfiguration.addAllowedHeader("*");

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", corsConfiguration);
        return source;
    }
}
