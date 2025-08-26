package com.delivery.mydelivery.database.entities.ordermanage;

import com.delivery.mydelivery.database.entities.accountmanage.Employee;
import com.delivery.mydelivery.database.entities.ordermanage.payment.PaymentMethod;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "order_finish")
public class OrderFinish {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "comment")
    private String comment;
    @Column(name = "delivered_at")
    private LocalDateTime deliveredAt;
    @Column(name = "items_count")
    private Integer itemsCount;
    @Column(name = "tips")
    private BigDecimal tips;
    @Column(name = "delivery_price")
    private BigDecimal deliveryPrice;
    @ManyToOne
    @JoinColumn(name = "payment_method_id", nullable = false)
    private PaymentMethod paymentMethod;
    @ManyToOne
    @JoinColumn(name = "order_id", nullable = false)
    private ClientOrder clientOrder;
    @ManyToOne
    @JoinColumn(name = "courier_id", nullable = false)
    private Employee employee;
    @PrePersist
    protected void onCreate() {
        deliveredAt = LocalDateTime.now();
    }

}
