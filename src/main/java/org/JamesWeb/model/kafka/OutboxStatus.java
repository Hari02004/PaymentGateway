package org.JamesWeb.model.kafka;

public enum OutboxStatus {
    PENDING,
    PUBLISHED,
    FAILED,
    PROCESSING,
    DEAD
}
