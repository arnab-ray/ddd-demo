package io.demo.domain.wh.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.demo.domain.common.ListingId;
import io.demo.domain.common.Quantity;
import io.demo.domain.common.WarehouseId;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "inventories")
public class Inventory implements Serializable {
    @EmbeddedId
    private InventoryId id;

    @Embedded
    @AttributeOverride(name = "id", column = @Column(name = "listing_id"))
    private ListingId listingId;

    @Embedded
    @AttributeOverride(name = "id", column = @Column(name = "wh_id"))
    private WarehouseId warehouseId;

    @Embedded
    @AttributeOverride(name = "value", column = @Column(name = "total_quantity"))
    private Quantity totalQuantity;

    @Embedded
    @AttributeOverride(name = "value", column = @Column(name = "available_quantity"))
    private Quantity availableQuantity;

    @CreationTimestamp
    @JsonProperty("created_at")
    @JsonFormat(timezone = "Asia/Kolkata")
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @JsonProperty("updated_at")
    @JsonFormat(timezone = "Asia/Kolkata")
    private LocalDateTime updatedAt;

    @Version
    private Long version;

    public boolean isAvailable() {
        return totalQuantity.isGreaterThanOrEqualTo(availableQuantity);
    }

    public void reserveInventory(Quantity quantity) {
        var quantityAvailable = this.availableQuantity.reduce(quantity);
        this.setAvailableQuantity(quantityAvailable);
    }

    public boolean exactMatch(WarehouseId warehouseId, Quantity quantity) {
        return this.warehouseId.equals(warehouseId) && this.availableQuantity.isGreaterThanOrEqualTo(quantity);
    }

    public boolean hasAvailableQuantity(Quantity quantity) {
        return this.availableQuantity.isGreaterThanOrEqualTo(quantity);
    }
}
