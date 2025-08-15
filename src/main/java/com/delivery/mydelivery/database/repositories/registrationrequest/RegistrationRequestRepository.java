package com.delivery.mydelivery.database.repositories.registrationrequest;

import com.delivery.mydelivery.database.entities.registrationrequest.RegistrationRequest;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RegistrationRequestRepository extends JpaRepository<RegistrationRequest, Long> {
}
