package com.delivery.mydelivery.database.entities.orderimages;

import com.delivery.mydelivery.database.entities.ordermanage.OrderPickup;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "order_image_pickup")
public class OrderImagePickup {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "order_image_id", nullable = false)
    private OrderImage orderImage;
    @ManyToOne
    @JoinColumn(name = "order_pickup_id", nullable = false)
    private OrderPickup orderPickup;
}
