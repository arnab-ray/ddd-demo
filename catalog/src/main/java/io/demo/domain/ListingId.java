package io.demo.domain;

import com.github.f4b6a3.ulid.UlidCreator;

public record ListingId(String id) {
    public ListingId {
        if (id == null || id.isBlank()) {
            throw new IllegalArgumentException("ID should not be null or empty");
        }
    }

    public ListingId() {
        this(UlidCreator.getMonotonicUlid().toString());
    }
}
