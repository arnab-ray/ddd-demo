package io.demo.domain;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.util.Assert;

@Getter
@Setter
@ToString
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "books")
public class Book extends Listing {
    private String title;

    @Column(name = "price")
    private Integer priceInPaise;

    @Embedded
    @AttributeOverride(name = "value", column = @Column(name = "isbn"))
    private Barcode barcode;

    public Book(String title, Barcode barcode, Integer priceInPaise) {
        super();
        Assert.notNull(title, "title must be present");
        Assert.notNull(barcode, "barcode must be present");
        Assert.notNull(priceInPaise, "price must be present");

        this.title = title;
        this.barcode = barcode;
        this.priceInPaise = priceInPaise;
    }

    public String getListingId() {
        return super.getId().id();
    }
}
