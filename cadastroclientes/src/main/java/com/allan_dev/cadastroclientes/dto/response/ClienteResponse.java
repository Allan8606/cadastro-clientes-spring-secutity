package com.allan_dev.cadastroclientes.dto.response;

import lombok.Builder;

@Builder
public record ClienteResponse(Long funcionarioId, String nome, String email, String endereco) {
}
