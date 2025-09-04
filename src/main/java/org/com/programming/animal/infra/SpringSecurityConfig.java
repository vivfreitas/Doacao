package org.com.programming.animal.infra;

import org.com.programming.animal.infra.jwt.AuthToken;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SpringSecurityConfig {

    /* Será o nosso provedor para verificar o nosso usuário e senha, de acordo com o que configuramos no nosso UserDetailsConfig. */
    private final AuthenticationProvider authenticationProvider;
    private final AuthToken authToken;

    public SpringSecurityConfig(AuthenticationProvider authenticationProvider, AuthToken authToken){
        /* Por enquanto não iremos implementa-lo. */
        this.authenticationProvider = authenticationProvider;
        this.authToken = authToken;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception{
        return httpSecurity
                .csrf(AbstractHttpConfigurer:: disable)
                .authorizeHttpRequests(auth ->{
                    auth.requestMatchers("/api/createUser").permitAll();
                    auth.requestMatchers("/api/AllAnimal").permitAll();
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
}
