package com.delivery.mydelivery.database.entities.ordermanage;

import com.delivery.mydelivery.database.entities.accountmanage.Employee;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "order_pickup")
public class OrderPickup {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @Column(name = "taken_at", updatable = false, nullable = false)
    private LocalDateTime takenAt;
    @Column(name = "comment")
    private String comment;
    @Column(name = "items_count")
    private Integer itemsCount;

    @ManyToOne
    @JoinColumn(name = "order_id", nullable = false)
    private ClientOrder clientOrder;

    @ManyToOne
    @JoinColumn(name = "courier_id", nullable = false)
    private Employee courier;

    @PrePersist
    protected void onCreate(){
        takenAt = LocalDateTime.now();
    }
}
