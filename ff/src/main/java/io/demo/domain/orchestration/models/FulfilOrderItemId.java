package io.demo.domain.orchestration.models;

import com.github.f4b6a3.ulid.UlidCreator;

public record FulfilOrderItemId(String id) {
    public FulfilOrderItemId {
        if (id == null || id.isBlank()) {
            throw new IllegalArgumentException("ID should not be null or empty");
        }
    }

    public FulfilOrderItemId() {
        this(UlidCreator.getMonotonicUlid().toString());
    }
}
