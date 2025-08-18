package com.delivery.mydelivery.database.services.registrationrequest;

import com.delivery.mydelivery.database.entities.accountmanage.role.RoleEnum;
import com.delivery.mydelivery.database.entities.ordermanage.status.StatusEnum;
import com.delivery.mydelivery.database.entities.organization.Organization;
import com.delivery.mydelivery.database.entities.registrationrequest.RegistrationRequest;
import com.delivery.mydelivery.database.entities.registrationrequest.RequestStatus;
import com.delivery.mydelivery.database.entities.registrationrequest.RequestStatusEnum;
import com.delivery.mydelivery.database.repositories.registrationrequest.RegistrationRequestRepository;
import com.delivery.mydelivery.dto.auth.AccountData;
import com.delivery.mydelivery.dto.registrationrequest.RegistrationRequestDTO;
import jakarta.persistence.EntityNotFoundException;
import org.checkerframework.checker.units.qual.A;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class RegistrationRequestService {
    @Autowired
    RegistrationRequestRepository repository;
    @Autowired
    RequestStatusService requestStatusService;

    public void createRegistrationRequest(RegistrationRequest registrationRequest){
        RequestStatus requestStatus = requestStatusService.getRequestStatusByName(RequestStatusEnum.PENDING);
        registrationRequest.setRequestStatus(requestStatus);
        repository.save(registrationRequest);
    }
    @Transactional
    public void changeStatus(RegistrationRequestDTO registrationRequestDTO) {
        RegistrationRequest request = repository.findById(registrationRequestDTO.getId())
                .orElseThrow(EntityNotFoundException::new);
        request.setRequestStatus(requestStatusService.getRequestStatusByName(registrationRequestDTO.getRequestStatus()));
    }
    public List<RegistrationRequest> getRegistrationRequestsByStatus(RequestStatusEnum status) {
        RequestStatus requestStatus = requestStatusService.getRequestStatusByName(status);
        return repository.findRegistrationRequestsByRequestStatus(requestStatus);
    }
}
