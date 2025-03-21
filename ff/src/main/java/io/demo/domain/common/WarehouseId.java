package io.demo.domain.common;

public record WarehouseId(String id) {
    public WarehouseId {
        if (id == null || id.isBlank()) {
            throw new IllegalArgumentException("ID should not be null or empty");
        }
    }
}
