package io.demo.domain.wh.models;

import com.github.f4b6a3.ulid.UlidCreator;

public record ReservationId(String id) {
    public ReservationId {
        if (id == null || id.isBlank()) {
            throw new IllegalArgumentException("ID should not be null or empty");
        }
    }

    public ReservationId() {
        this(UlidCreator.getMonotonicUlid().toString());
    }
}
