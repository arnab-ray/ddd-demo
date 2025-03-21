package io.demo.domain.orchestration.repositories;

import io.demo.domain.common.OrderId;
import io.demo.domain.orchestration.models.FulfilOrder;
import io.demo.domain.orchestration.models.FulfilOrderId;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface FFOrderRepository extends JpaRepository<FulfilOrder, Long> {
    Optional<FulfilOrder> findByOrderId(OrderId orderId);

    Optional<FulfilOrder> findById(FulfilOrderId fulfilOrderId);
}
