package com.delivery.mydelivery.database.entities.accountmanage;

import com.delivery.mydelivery.database.entities.accountmanage.role.Role;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;

import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "account")
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @Column(name = "email")
    String email;
    @Column(name = "password")
    String password;
    @ManyToOne
    @JoinColumn(name = "role_id", nullable = false)
    private Role role;
    @OneToOne()
    @JoinColumn(name = "employee_id", nullable = true)
    private Employee employee;
}
