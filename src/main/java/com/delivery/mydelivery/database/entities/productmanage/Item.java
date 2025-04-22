package com.delivery.mydelivery.database.entities.productmanage;

import com.delivery.mydelivery.database.entities.ordermanage.ClientOrder;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.query.Order;

import java.math.BigDecimal;

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
    @ManyToOne
    @JoinColumn(name = "material_id")
    private Material material;
    @ManyToOne
    @JoinColumn(name = "order_id")
    private ClientOrder order;
}
