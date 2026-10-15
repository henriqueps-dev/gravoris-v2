package br.com.gravoris.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

/**
 * DTO usado para criar e atualizar clientes a partir do frontend.
 */
public record ClienteRequest(
        @NotBlank(message = "Nome é obrigatório") String name,
        @NotBlank(message = "E-mail é obrigatório") @Email(message = "E-mail inválido") String email,
        String password,
        String phone,
        @NotBlank(message = "CPF é obrigatório") String cpf
) {
}
