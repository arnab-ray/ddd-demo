package io.demo.domain.orchestration.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.demo.domain.common.ListingId;
import io.demo.domain.common.OrderItemId;
import io.demo.domain.common.Quantity;
import io.demo.domain.common.WarehouseId;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.util.Assert;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "ff_order_items")
public class FulfilOrderItem implements Serializable {
    @EmbeddedId
    private FulfilOrderItemId id;

    @Embedded
    @AttributeOverride(name = "id", column = @Column(name = "fulfil_order_id"))
    @ManyToOne
    @JoinColumn(name = "fulfil_order_id", nullable = false)
    private FulfilOrderId fulfilOrderId;

    @Embedded
    @AttributeOverride(name = "id", column = @Column(name = "order_item_id"))
    private OrderItemId orderItemId;

    @Column(name = "price")
    private Integer priceInPaise;

    @Embedded
    @AttributeOverride(name = "value", column = @Column(name = "quantity"))
    private Quantity quantity;

    @Embedded
    @AttributeOverride(name = "id", column = @Column(name = "listing_id"))
    private ListingId listingId;

    @Embedded
    @AttributeOverride(name = "id", column = @Column(name = "warehouse"))
    private WarehouseId warehouse;

    @Enumerated(value = EnumType.STRING)
    private OrderItemState status;

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

    public FulfilOrderItem(
            FulfilOrderId fulfilOrderId,
            OrderItemId orderItemId,
            Integer priceInPaise,
            Quantity quantity,
            ListingId listingId,
            WarehouseId warehouse) {
        Assert.notNull(fulfilOrderId, "fulfilOrderId must be present");
        Assert.notNull(orderItemId, "orderItemId must be present");
        Assert.notNull(priceInPaise, "priceInPaise must be present");
        Assert.notNull(quantity, "quantity must be present");
        Assert.notNull(listingId, "listingId must be present");
        Assert.notNull(warehouse, "warehouse must be present");

        this.id = new FulfilOrderItemId();
        this.fulfilOrderId = fulfilOrderId;
        this.orderItemId = orderItemId;
        this.priceInPaise = priceInPaise;
        this.quantity = quantity;
        this.listingId = listingId;
        this.warehouse = warehouse;
        this.status = OrderItemState.INITIATED;
    }

    public boolean isNotReservedYet() {
        return OrderItemState.INITIATED.equals(this.getStatus());
    }

    public void reserve() {
        this.status = OrderItemState.RESERVED;
    }
}
