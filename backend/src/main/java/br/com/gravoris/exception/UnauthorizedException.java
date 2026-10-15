package br.com.gravoris.exception;

/**
 * Exceção para falhas de autenticação.
 */
public class UnauthorizedException extends RuntimeException {
    public UnauthorizedException(String message) {
        super(message);
    }
}
