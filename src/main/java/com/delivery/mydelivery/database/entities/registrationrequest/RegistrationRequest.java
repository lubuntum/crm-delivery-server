package com.delivery.mydelivery.database.entities.registrationrequest;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.checkerframework.checker.units.qual.C;

import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "registration_request")
public class RegistrationRequest {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @Column(name = "director_name")
    private String directorName;
    @Column(name = "director_second_name")
    private String directorSecondName;
    @Column(name = "director_patronymic")
    private String directorPatronymic;
    @Column(name = "brand_name")
    private String brandName;
    @Column(name = "city")
    private String city;
    @Column(name = "email", unique = true)
    private String email;
    @Column(name = "phone_number")
    private String phoneNumber;
    @Column(name = "submitted_at", nullable = false)
    private LocalDateTime submittedAt;
    @ManyToOne
    @JoinColumn(name = "request_status_id", nullable = false)
    private RequestStatus requestStatus;
    @PrePersist
    protected void onCreate() {
        submittedAt =  LocalDateTime.now();
    }
}
