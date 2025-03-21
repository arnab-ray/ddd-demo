package io.demo.domain.orchestration.models;

public enum OrderItemState {
    INITIATED,
    RESERVED,
    PICKED,
    PACKED,
    DISPATCHED,
    DELIVERED
}
