package com.allan_dev.cadastroclientes.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record ClienteRequest(
        @NotBlank
        String nome,

        @Email
        @NotBlank
        String email,

        @NotBlank
        String endereco) {
}
