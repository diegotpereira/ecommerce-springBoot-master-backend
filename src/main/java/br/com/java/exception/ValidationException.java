package br.com.java.exception;

public class ValidationException extends RuntimeException {

    private static final long serialVersionUID = 6064663768170825752L;
    
    public ValidationException(String message, String string) {
        super(string);
    }

    public ValidationException(String message, Throwable cause) {
        super(message, cause);
    }
}
