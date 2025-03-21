package io.demo.domain.wh.services;

import io.demo.Either;
import io.demo.Left;
import io.demo.Right;
import io.demo.domain.common.ListingId;
import io.demo.domain.common.OrderItemId;
import io.demo.domain.common.Quantity;
import io.demo.domain.common.ReservationError;
import io.demo.domain.wh.models.Inventory;
import io.demo.domain.wh.models.Reservation;
import io.demo.domain.wh.repositories.InventoryRepository;
import io.demo.domain.wh.repositories.ReservationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class WarehouseService implements IWarehouseService {

    private final InventoryRepository inventoryRepository;
    private final ReservationRepository reservationRepository;

    @Autowired
    public WarehouseService(InventoryRepository inventoryRepository, ReservationRepository reservationRepository) {
        this.inventoryRepository = inventoryRepository;
        this.reservationRepository = reservationRepository;
    }

    @Override
    public List<Inventory> getAvailableInventories(ListingId listingId) {
        return inventoryRepository.findByListingId(listingId).stream().filter(Inventory::isAvailable).toList();
    }

    @Override
    @Transactional
    public Either<Reservation, ReservationError> reserveInventory(Inventory inventory, OrderItemId orderItemId, Quantity quantity) {
        if (inventory == null) {
            return new Right<>(new ReservationError("Inventory not found"));
        }

        inventory.reserveInventory(quantity);
        Reservation reservation = new Reservation(inventory.getListingId(), inventory.getWarehouseId(), orderItemId, quantity);

        inventoryRepository.save(inventory);
        reservationRepository.save(reservation);

        return new Left<>(reservation);
    }
}
