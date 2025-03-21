package io.demo.infrastructure;

import io.demo.domain.common.*;
import io.demo.domain.orchestration.models.OrderDetails;

import java.util.List;

public class OrderMapper {
    public static OrderDetails toOrderDetails(Order order) {
        return new OrderDetails(new OrderId(order.getOrderId()), order.getAddressId(), getOrderItemDetails(order));
    }

    public static List<OrderDetails.OrderItemDetails> getOrderItemDetails(Order order) {
        return order.getOrderItems()
                .stream()
                .map(
                        orderItem ->
                                new OrderDetails.OrderItemDetails(
                                        new OrderItemId(orderItem.getOrderItemId()),
                                        orderItem.getPriceInPaise(),
                                        new Quantity(orderItem.getQuantity()),
                                        new ListingId(orderItem.getListingId()),
                                        new WarehouseId(orderItem.getWarehouseId())
                                )
                )
                .toList();
    }
}
