package com.allan_dev.cadastroclientes.config;


import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final SecurityFilter securityFilter; // <--- 1. Chamamos a nossa catraca!


    //1- Filtro de Segurança
    @Bean
    public SecurityFilterChain securityFilterChain (HttpSecurity http) throws Exception{

        return http
                // Desliga uma proteção contra ataques de formulário (pra API, geralmente desligamos)
                .csrf(csrf -> csrf.disable())
                // Diz: "Não guarde estado, cada requisição é nova" (Padrão de API REST)
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(req -> {
                    // 1. Login e Cadastro continuam livres pra todo mundo (Porta da Rua)
                    req.requestMatchers(HttpMethod.POST, "/login").permitAll();
                    req.requestMatchers(HttpMethod.POST, "/funcionario").permitAll();
                    req.requestMatchers(HttpMethod.GET, "/funcionario/**").permitAll();

                    // 2. REGRAS VIP (Só o ADMIN pode)
                    // "Segurança, se alguém tentar EXCLUIR (DELETE) um cliente, verifique se tem a autoridade ADMIN"
                    req.requestMatchers(HttpMethod.DELETE, "/cliente/**").hasAuthority("ADMIN");

                    // "Segurança, se alguém tentar CADASTRAR (POST) um cliente novo, só ADMIN passa"
                    req.requestMatchers(HttpMethod.POST, "/cliente").hasAuthority("ADMIN");

                    // 3. REGRAS COMUNS (BASIC ou ADMIN podem)
                    // "Pra ver a lista de clientes (GET), qualquer um com pulseira pode"
                    req.requestMatchers(HttpMethod.GET, "/cliente").authenticated(); // Ou .hasAuthority("BASIC") se quiser restringir

                    // 4. O resto que sobrar, tem que estar logado
                    req.anyRequest().authenticated();
                })
                // 3. INSTALAMOS A CATRACA:
                // Dizemos: "Rode o nosso filtro ANTES do filtro padrão do Spring"
                .addFilterBefore(securityFilter, UsernamePasswordAuthenticationFilter.class)
                .build();

    }

    // 2. O Gerenciador de Autenticação (O Chefe que carimba o passaporte)
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }


    // 3. O Criptografador de Senhas
    @Bean
    public PasswordEncoder passwordEncoder(){
        //Usa a criptografia BCrypt (muito segura)
        return new BCryptPasswordEncoder();
    }
}
