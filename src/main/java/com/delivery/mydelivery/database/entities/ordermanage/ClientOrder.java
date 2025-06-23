package com.delivery.mydelivery.database.entities.ordermanage;

import com.delivery.mydelivery.database.entities.accountmanage.Client;
import com.delivery.mydelivery.database.entities.ordermanage.status.Status;
import com.delivery.mydelivery.database.entities.organization.Organization;
import com.delivery.mydelivery.database.entities.productmanage.Item;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "client_order")
public class ClientOrder {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @Column(name = "serial_number")
    private String serialNumber;
    @Column(name = "address")
    private String address;
    @Column(name = "comment")
    private String comment;
    @Column(name = "created_at", updatable = false, nullable = false)
    LocalDateTime createdAt;
    @Column(name = "total_price")
    BigDecimal totalPrice;
    @Column(name = "is_active")
    Boolean isActive;
    @ManyToOne
    @JoinColumn(name = "client_id")
    private Client client;
    @ManyToOne
    @JoinColumn(name = "status_id", nullable = false)
    private Status status;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "organization_id", nullable = false)
    private Organization organization;
    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Item> itemList;
    @PrePersist
    protected void onCreate(){
        createdAt = LocalDateTime.now();
    }
}
