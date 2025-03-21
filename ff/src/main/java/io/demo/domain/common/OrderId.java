package io.demo.domain.common;

public record OrderId(String id) {
    public OrderId {
        if (id == null || id.isBlank()) {
            throw new IllegalArgumentException("ID should not be null or empty");
        }
    }

}
