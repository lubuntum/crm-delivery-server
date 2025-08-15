package com.delivery.mydelivery.database.repositories.registrationrequest;

import com.delivery.mydelivery.database.entities.registrationrequest.RequestStatus;
import com.delivery.mydelivery.database.entities.registrationrequest.RequestStatusEnum;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RequestStatusRepository extends JpaRepository<RequestStatus, Long> {
    RequestStatus findByName(RequestStatusEnum requestStatusEnum);
}
