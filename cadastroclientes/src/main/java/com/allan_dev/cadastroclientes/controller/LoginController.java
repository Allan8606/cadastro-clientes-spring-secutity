package com.allan_dev.cadastroclientes.controller;


import com.allan_dev.cadastroclientes.config.TokenService;
import com.allan_dev.cadastroclientes.dto.DadosTokenJWT;
import com.allan_dev.cadastroclientes.dto.request.LoginRequest;
import com.allan_dev.cadastroclientes.entity.Funcionario;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/login")
@RequiredArgsConstructor
public class LoginController {



    private final AuthenticationManager authenticationManager;

    private final TokenService tokenService;


    @PostMapping
    public ResponseEntity<Object> efetuarLogin(@RequestBody LoginRequest login){
        // 1. Transformar os dados do DTO em um token do Spring (UsernamePasswordAuthenticationToken)
        // Isso é como pegar o RG e a Senha e grampear num formulário padrão do Spring.
       var tokenDeLogin = new UsernamePasswordAuthenticationToken(login.email(), login.senha());

        // 2. Entregar pro Chefe (Manager) e pedir pra ele autenticar
        // O manager vai chamar o seu AutenticacaoService, que vai no banco, que checa a senha... faz tudo sozinho!
        var autenticacao = authenticationManager.authenticate(tokenDeLogin);

        //3. Pegamos o usuário que o chefe validou
        var usuarioLogado = (Funcionario) autenticacao.getPrincipal();

        // 4. Geramos a pulseira (Token) para ele
        var tokenJWT = tokenService.gerarToken(usuarioLogado);

        // 5. Se der certo, devolvemos OK
        return ResponseEntity.ok(new DadosTokenJWT(tokenJWT));
    }
}
