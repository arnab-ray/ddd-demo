package io.demo.domain.orchestration.models;

import com.github.f4b6a3.ulid.UlidCreator;

public record FulfilOrderId(String id) {
    public FulfilOrderId {
        if (id == null || id.isBlank()) {
            throw new IllegalArgumentException("ID should not be null or empty");
        }
    }

    public FulfilOrderId() {
        this(UlidCreator.getMonotonicUlid().toString());
    }
}
