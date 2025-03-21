package io.demo.domain.wh.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.demo.domain.common.ListingId;
import io.demo.domain.common.OrderItemId;
import io.demo.domain.common.Quantity;
import io.demo.domain.common.WarehouseId;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.util.Assert;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "reservations")
public class Reservation implements Serializable {
    @EmbeddedId
    private ReservationId id;

    @Embedded
    @AttributeOverride(name = "id", column = @Column(name = "listing_id"))
    private ListingId listingId;

    @Embedded
    @AttributeOverride(name = "id", column = @Column(name = "wh_id"))
    private WarehouseId warehouseId;

    @Embedded
    @AttributeOverride(name = "id", column = @Column(name = "order_item_id"))
    private OrderItemId orderItemId;

    @Embedded
    @AttributeOverride(name = "value", column = @Column(name = "quantity"))
    private Quantity quantity;

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

    public Reservation(ListingId listingId, WarehouseId warehouseId, OrderItemId orderItemId, Quantity quantity) {
        Assert.notNull(listingId, "listingId must be present");
        Assert.notNull(warehouseId, "warehouseId must be present");
        Assert.notNull(orderItemId, "orderItemId must be present");
        Assert.notNull(quantity, "quantity must be present");

        this.id = new ReservationId();
        this.orderItemId = orderItemId;
        this.quantity = quantity;
        this.listingId = listingId;
        this.warehouseId = warehouseId;
    }
}
