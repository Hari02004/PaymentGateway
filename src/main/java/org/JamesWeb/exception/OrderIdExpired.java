package org.JamesWeb.exception;

public class OrderIdExpired extends RuntimeException {
    public OrderIdExpired(String message) {
        super(message);
    }
}
