package com.delivery.mydelivery.database.entities.productmanage;

import com.delivery.mydelivery.database.entities.orderimages.OrderImage;
import com.delivery.mydelivery.database.entities.ordermanage.ClientOrder;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.query.Order;

import java.math.BigDecimal;
import java.util.LinkedList;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "item")
public class Item {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @Column(name = "price", precision = 10, scale = 2)
    private BigDecimal price;
    @Column(name = "price_per_unit", precision = 10, scale = 2, nullable = false)
    private BigDecimal pricePerUnit;
    @Column(name = "additional_price", precision = 10, scale = 2, nullable = true)
    private BigDecimal additionalPrice;
    @Column(name = "size")
    private Double size;
    @Column(name = "width")
    private Double width;
    @Column(name = "height")
    private Double height;
    @Column(name = "comment")
    private String comment;
    @Column(name = "is_ready")
    private Boolean isReady;
    @ManyToOne
    @JoinColumn(name = "material_id")
    private Material material;
    @ManyToOne
    @JoinColumn(name = "order_id")
    private ClientOrder order;
    @ManyToMany
    @JoinTable(
            name = "order_image_item",
            joinColumns = @JoinColumn(name = "item_id"),
            inverseJoinColumns = @JoinColumn(name = "order_image_id"))
    private List<OrderImage> orderImages = new LinkedList<>();
}
