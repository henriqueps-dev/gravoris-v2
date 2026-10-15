package br.com.gravoris.exception;

import java.time.LocalDateTime;

/**
 * Payload usado para padronizar respostas de erro.
 */
public record ErrorResponse(String error, int status, LocalDateTime timestamp) {
}
