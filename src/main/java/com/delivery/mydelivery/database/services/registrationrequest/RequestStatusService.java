package com.delivery.mydelivery.database.services.registrationrequest;

import com.delivery.mydelivery.database.entities.registrationrequest.RequestStatus;
import com.delivery.mydelivery.database.entities.registrationrequest.RequestStatusEnum;
import com.delivery.mydelivery.database.repositories.registrationrequest.RequestStatusRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RequestStatusService {
    @Autowired
    RequestStatusRepository repository;

    public RequestStatus getRequestStatusByName(RequestStatusEnum name) {
        return repository.findByName(name);
    }
}
