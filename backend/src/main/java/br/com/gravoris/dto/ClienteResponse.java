package br.com.gravoris.dto;

/**
 * DTO de saída para dados de cliente sem expor a senha.
 */
public record ClienteResponse(
        Long id,
        String name,
        String email,
        String phone,
        String cpf,
        String dataNascimento,
        boolean ativo
) {
}
