package io.demo.domain.orchestration.models;

import io.demo.domain.common.*;

import java.util.List;

public record OrderDetails(OrderId orderId, String addressId, List<OrderItemDetails>orderItems) {
    public record OrderItemDetails(
            OrderItemId orderItemId,
            Integer priceInPaise,
            Quantity quantity,
            ListingId listingId,
            WarehouseId warehouseId
    ) {}
}
