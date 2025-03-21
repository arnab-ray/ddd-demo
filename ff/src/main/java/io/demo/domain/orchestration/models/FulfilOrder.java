package io.demo.domain.orchestration.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.demo.domain.common.OrderId;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.domain.AbstractAggregateRoot;
import org.springframework.util.Assert;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@ToString
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "ff_orders")
public class FulfilOrder extends AbstractAggregateRoot<FulfilOrder> implements Serializable {
    @EmbeddedId
    private FulfilOrderId id;

    @Embedded
    @AttributeOverride(name = "id", column = @Column(name = "order_id"))
    private OrderId orderId;

    @Column(name = "address_id")
    private String addressId;

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

    @OneToMany(mappedBy = "fulfilOrderId", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<FulfilOrderItem> fulfilOrderItems;

    public FulfilOrder(OrderId orderId, String addressId) {
        super();
        Assert.notNull(orderId, "orderId must be present");
        Assert.notNull(addressId, "addressId must be present");

        this.id = new FulfilOrderId();
        this.orderId = orderId;
        this.addressId = addressId;
    }
}
