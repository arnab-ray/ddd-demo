package io.demo.domain.common;

public record Quantity(Integer value) {
    public Quantity {
        if (value == null) {
            throw new IllegalArgumentException("value should not be null");
        }
    }

    public boolean isGreaterThan(Quantity other) {
        return this.value > other.value();
    }

    public boolean isGreaterThanOrEqualTo(Quantity other) {
        return this.value >= other.value();
    }

    public Quantity reduce(Quantity other) {
        return new Quantity(this.value - other.value());
    }
}
