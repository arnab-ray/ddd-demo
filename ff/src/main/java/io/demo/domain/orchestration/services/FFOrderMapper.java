package io.demo.domain.orchestration.services;

import io.demo.domain.orchestration.models.FulfilOrder;
import io.demo.domain.orchestration.models.FulfilOrderItem;
import io.demo.domain.orchestration.models.OrderDetails;

import java.util.List;

public class FFOrderMapper {
    public static FulfilOrder toFulfilOrder(OrderDetails order) {
        var ffOrder = new FulfilOrder(order.orderId(), order.addressId());
        ffOrder.setFulfilOrderItems(toFulfilOrderItems(order, ffOrder));

        return ffOrder;
    }

    public static List<FulfilOrderItem> toFulfilOrderItems(OrderDetails order, FulfilOrder fulfilOrder) {
        return order.orderItems()
                .stream()
                .map(
                        orderItem ->
                                new FulfilOrderItem(
                                        fulfilOrder.getId(),
                                        orderItem.orderItemId(),
                                        orderItem.priceInPaise(),
                                        orderItem.quantity(),
                                        orderItem.listingId(),
                                        orderItem.warehouseId())
                )
                .toList();
    }
}
