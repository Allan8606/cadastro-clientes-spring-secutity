package com.allan_dev.cadastroclientes.dto.response;

import com.allan_dev.cadastroclientes.enums.PerfilFuncinario;
import lombok.Builder;

@Builder
public record FuncionarioResponse(Long funcionarioId,String nome, String email, PerfilFuncinario perfil, String senha) {
}
