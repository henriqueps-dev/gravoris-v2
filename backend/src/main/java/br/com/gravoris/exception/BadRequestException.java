package br.com.gravoris.exception;

/**
 * Exceção lançada quando o cliente envia uma requisição inválida.
 */
public class BadRequestException extends RuntimeException {
    public BadRequestException(String message) {
        super(message);
    }
}
