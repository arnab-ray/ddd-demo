package io.demo.domain.wh.repositories;

import io.demo.domain.common.ListingId;
import io.demo.domain.wh.models.Inventory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface InventoryRepository extends JpaRepository<Inventory, Long> {
    List<Inventory> findByListingId(ListingId listingId);
}
