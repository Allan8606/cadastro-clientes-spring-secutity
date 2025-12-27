package com.allan_dev.cadastroclientes.dto.request;

import com.allan_dev.cadastroclientes.enums.PerfilFuncinario;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;


public record FuncionarioRequest(
        @NotBlank String nome, //Regra: Não pode ser vazio

        @Email //Regra: Tem que ter formato de email
        @NotBlank
        String email,

        String senha,
        PerfilFuncinario perfil) {
}
