package org.JamesWeb.exception;

public class ApiExceptionHandler extends RuntimeException {
    public ApiExceptionHandler(String message) {
        super(message);
    }
}
