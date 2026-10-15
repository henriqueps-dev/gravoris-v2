package br.com.gravoris.dto;

/**
 * Resposta enviada ao frontend após autenticação.
 */
public record LoginResponse(
        Long id,
        String name,
        String email,
        String token
) {
}
