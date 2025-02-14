package org.example.exception;

public class DataAccessException extends RuntimeException {

    // Construtor que aceita apenas a mensagem de erro
    public DataAccessException(String message) {
        super(message);
    }

    // Construtor que aceita mensagem e causa do erro
    public DataAccessException(String message, Throwable cause) {
        super(message, cause);
    }
}