package com.delivery.mydelivery.database.services.organization;

import com.delivery.mydelivery.database.entities.accountmanage.accountstatus.ActiveStatus;
import com.delivery.mydelivery.database.entities.accountmanage.accountstatus.ActiveStatusEnum;
import com.delivery.mydelivery.database.entities.organization.Organization;
import com.delivery.mydelivery.database.repositories.accountmanage.ActiveStatusRepository;
import com.delivery.mydelivery.database.repositories.organization.OrganizationRepository;
import com.delivery.mydelivery.dto.organization.OrganizationDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrganizationService {
    @Autowired
    OrganizationRepository organizationRepository;
    @Autowired
    ActiveStatusRepository activeStatusRepository;

    public Organization getOrganizationById(Long id) {
        return organizationRepository.findById(id).orElseThrow(NullPointerException::new);
    }
    public void createOrganization(OrganizationDTO organizationDTO) {
        Organization organization = new Organization();
        ActiveStatus activeStatus = activeStatusRepository.findActiveStatusByName(ActiveStatusEnum.ENABLED);

        organization.setName(organizationDTO.getName());
        organization.setOrganizationActiveStatus(activeStatus);
        organizationDTO.setId(organizationRepository.save(organization).getId());
        organizationDTO.getDirectorData().setOrganizationId(organization.getId());
    }
}
