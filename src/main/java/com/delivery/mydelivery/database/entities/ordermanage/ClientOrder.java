package com.delivery.mydelivery.database.entities.ordermanage;

import com.delivery.mydelivery.database.entities.accountmanage.Client;
import com.delivery.mydelivery.database.entities.ordermanage.status.Status;
import com.delivery.mydelivery.database.entities.organization.Organization;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "client_order")
public class ClientOrder {
    @Id
    @GeneratedValue
    @Column(name = "id")
    private Long id;
    @Column(name = "address")
    private String address;
    @Column(name = "comment")
    private String comment;
    @Column(name = "created_at")
    LocalDate createdAt;
    @ManyToOne
    @JoinColumn(name = "client_id", nullable = false)
    private Client client;
    @ManyToOne
    @JoinColumn(name = "status_id", nullable = false)
    private Status status;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "organization_id", nullable = false)
    private Organization organization;
}
