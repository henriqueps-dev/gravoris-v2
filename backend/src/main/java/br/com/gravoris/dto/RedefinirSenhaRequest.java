package br.com.gravoris.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

/**
 * Payload usado para redefinir a senha de um cliente.
 */
public record RedefinirSenhaRequest(
        @NotBlank @Email String email,
        @NotBlank String newPassword
) {
}
