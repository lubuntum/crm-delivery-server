package com.delivery.mydelivery.database.services.organization;

import com.delivery.mydelivery.database.entities.accountmanage.accountstatus.ActiveStatus;
import com.delivery.mydelivery.database.entities.accountmanage.accountstatus.ActiveStatusEnum;
import com.delivery.mydelivery.database.entities.organization.Organization;
import com.delivery.mydelivery.database.repositories.accountmanage.ActiveStatusRepository;
import com.delivery.mydelivery.database.repositories.organization.OrganizationRepository;
import com.delivery.mydelivery.dto.organization.OrganizationDTO;
import com.delivery.mydelivery.dto.organization.OrganizationMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

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
    public void changeOrganizationActiveStatus(OrganizationDTO organizationDTO) {
        Organization organization = organizationRepository.findById(organizationDTO.getId())
                .orElseThrow(NullPointerException::new);
        ActiveStatus activeStatus = activeStatusRepository.findActiveStatusByName(organizationDTO.getActiveStatus());
        organization.setOrganizationActiveStatus(activeStatus);
        organizationRepository.save(organization);
    }
    public List<OrganizationDTO> getAllOrganizations() {
        List<Organization> organizationsList = organizationRepository.findAll();
        return organizationsList.stream()
                .map(OrganizationMapper::toDTO)
                .collect(Collectors.toList());
    }
    public void save(Organization organization) {
        organizationRepository.save(organization);
    }
}
