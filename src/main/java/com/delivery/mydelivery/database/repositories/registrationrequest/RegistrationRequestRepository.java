package com.delivery.mydelivery.database.repositories.registrationrequest;

import com.delivery.mydelivery.database.entities.registrationrequest.RegistrationRequest;
import com.delivery.mydelivery.database.entities.registrationrequest.RequestStatus;
import com.delivery.mydelivery.database.entities.registrationrequest.RequestStatusEnum;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RegistrationRequestRepository extends JpaRepository<RegistrationRequest, Long> {
    public List<RegistrationRequest> findRegistrationRequestsByRequestStatus(RequestStatus status);
}
