package io.demo.domain.wh.services;

import io.demo.Either;
import io.demo.domain.common.ListingId;
import io.demo.domain.common.OrderItemId;
import io.demo.domain.common.Quantity;
import io.demo.domain.common.ReservationError;
import io.demo.domain.wh.models.Inventory;
import io.demo.domain.wh.models.Reservation;

import java.util.List;

public interface IWarehouseService {
    List<Inventory> getAvailableInventories(ListingId listingId);

    Either<Reservation, ReservationError> reserveInventory(Inventory inventory, OrderItemId orderItemId, Quantity quantity);
}
