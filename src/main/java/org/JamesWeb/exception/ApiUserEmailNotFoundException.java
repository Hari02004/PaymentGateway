package org.JamesWeb.exception;

public class ApiUserEmailNotFoundException extends RuntimeException {
    public ApiUserEmailNotFoundException(String message) {
        super(message);
    }
}
