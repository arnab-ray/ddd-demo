package io.demo.domain.wh.models;

import com.github.f4b6a3.ulid.UlidCreator;

public record InventoryId(String id) {
    public InventoryId {
        if (id == null || id.isBlank()) {
            throw new IllegalArgumentException("ID should not be null or empty");
        }
    }

    public InventoryId() {
        this(UlidCreator.getMonotonicUlid().toString());
    }
}
