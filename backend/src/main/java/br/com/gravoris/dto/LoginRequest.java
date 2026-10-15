package br.com.gravoris.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

/**
 * Payload recebido no login do usuário.
 */
public record LoginRequest(
        @NotBlank @Email String email,
        @NotBlank String password
) {
}
