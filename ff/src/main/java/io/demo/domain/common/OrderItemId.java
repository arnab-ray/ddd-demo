package io.demo.domain.common;

public record OrderItemId(String id) {
    public OrderItemId {
        if (id == null || id.isBlank()) {
            throw new IllegalArgumentException("ID should not be null or empty");
        }
    }
}
