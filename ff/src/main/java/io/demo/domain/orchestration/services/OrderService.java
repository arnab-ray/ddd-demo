package io.demo.domain.orchestration.services;

import io.demo.domain.common.Quantity;
import io.demo.domain.orchestration.models.FulfilOrder;
import io.demo.domain.orchestration.models.FulfilOrderId;
import io.demo.domain.orchestration.models.FulfilOrderItem;
import io.demo.domain.orchestration.models.OrderDetails;
import io.demo.domain.orchestration.repositories.FFOrderRepository;
import io.demo.domain.wh.models.Inventory;
import io.demo.domain.wh.services.IWarehouseService;
import io.demo.infrastructure.adapters.CatalogService;
import io.demo.infrastructure.publishers.KafkaProducer;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static io.demo.domain.orchestration.models.Constants.FF_TOPIC;

@Service
public class OrderService implements IOrderService {
    private final FFOrderRepository ffOrderRepository;
    private final KafkaProducer kafkaProducer;
    private final IWarehouseService warehouseService;
    private final CatalogService catalogService;

    @Autowired
    public OrderService(
            FFOrderRepository ffOrderRepository,
            KafkaProducer kafkaProducer,
            IWarehouseService warehouseService,
            CatalogService catalogService) {
        this.ffOrderRepository = ffOrderRepository;
        this.kafkaProducer = kafkaProducer;
        this.warehouseService = warehouseService;
        this.catalogService = catalogService;
    }

    @Override
    @Transactional
    public void acceptOrder(OrderDetails order) {
        Optional<FulfilOrder> maybeFulfilOrder = ffOrderRepository.findByOrderId(order.orderId());

        if (maybeFulfilOrder.isEmpty()) {
            var fulfilOrder = FFOrderMapper.toFulfilOrder(order);
            ffOrderRepository.save(fulfilOrder);
            kafkaProducer.sendMessage(FF_TOPIC, fulfilOrder.getOrderId().id(), fulfilOrder.getOrderId().id());
        }
    }

    @Override
    @Transactional
    public void fulfilOrder(FulfilOrderId fulfilOrderId) {
        Optional<FulfilOrder> maybeFulfilOrder = ffOrderRepository.findById(fulfilOrderId);
        if (maybeFulfilOrder.isEmpty()) {
            return;
        }

        maybeFulfilOrder.get().getFulfilOrderItems().stream()
                .filter(it -> it.isNotReservedYet() && catalogService.isValidCatalog(it.getListingId()))
                .forEach( it -> {
                    var inventories = warehouseService.getAvailableInventories(it.getListingId());
                    var matchedWhInventory = inventories.stream()
                            .filter(inventory -> inventory.exactMatch(it.getWarehouse(), it.getQuantity()))
                            .findFirst();

                    if (matchedWhInventory.isPresent()) {
                        reserve(matchedWhInventory.get(), it, it.getQuantity());
                    } else {
                        var otherWhInventory =
                                inventories.stream()
                                        .filter(inventory -> inventory.hasAvailableQuantity(it.getQuantity()))
                                        .findFirst();
                        otherWhInventory.ifPresent(inventory -> reserve(inventory, it, it.getQuantity()));
                    }
                });

        ffOrderRepository.save(maybeFulfilOrder.get());
    }

    private void reserve(Inventory inventory, FulfilOrderItem fulfilOrderItem, Quantity quantity) {
        var response = warehouseService.reserveInventory(inventory, fulfilOrderItem.getOrderItemId(), quantity);
        if (response.isLeft()) {
            fulfilOrderItem.reserve();
        }
    }
}
