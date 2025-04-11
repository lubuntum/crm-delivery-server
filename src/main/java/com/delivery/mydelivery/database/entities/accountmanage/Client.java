package com.delivery.mydelivery.database.entities.accountmanage;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "client")
public class Client {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @Column(name = "name")
    private String name;
    @Column(name = "second_name")
    private String secondName;
    @Column(name = "patronymic")
    private String patronymic;
    //TODO make the same in DB
    @Column(name = "email", unique = true)
    private String email;
    @Column(name = "phone", unique = true)
    private String phone;

}
