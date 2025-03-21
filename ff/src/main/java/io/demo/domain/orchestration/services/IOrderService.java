package io.demo.domain.orchestration.services;

import io.demo.domain.orchestration.models.FulfilOrderId;
import io.demo.domain.orchestration.models.OrderDetails;

public interface IOrderService {
    void acceptOrder(OrderDetails orderDetails);

    void fulfilOrder(FulfilOrderId fulfilOrderId);
}
