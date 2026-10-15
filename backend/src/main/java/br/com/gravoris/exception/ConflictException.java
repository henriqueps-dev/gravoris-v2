package br.com.gravoris.exception;

/**
 * Exceção para conflitos de dados já existentes (ex.: e-mail ou CPF duplicado).
 */
public class ConflictException extends RuntimeException {
    public ConflictException(String message) {
        super(message);
    }
}
