package com.delivery.mydelivery.database.entities.organization;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "organization_details")
public class OrganizationDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "organization_id", unique = true, nullable = false)
    private Organization organization;

    @Column(name = "brand_name")
    private String brandName;

    @Column(name = "inn")
    private String inn;

    @Column(name = "kpp")
    private String kpp;

    @Column(name = "ogrn")
    private String ogrn;

    @Column(name = "legal_address")
    private String legalAddress;

    @Column(name = "current_account")
    private String currentAccount;

    @Column(name = "bank_name")
    private String bankName;

    @Column(name = "correspond_account")
    private String correspondAccount;

    @Column(name = "bic")
    private String bic;

    @Column(name = "phone_number")
    private String phoneNumber;

    @Column(name = "email_address")
    private String emailAddress;
}
