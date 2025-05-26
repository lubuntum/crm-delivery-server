package com.delivery.mydelivery.database.services.organization;

import com.delivery.mydelivery.database.entities.organization.Organization;
import com.delivery.mydelivery.database.repositories.organization.OrganizationRepository;
import com.delivery.mydelivery.dto.organization.OrganizationDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrganizationService {
    @Autowired
    OrganizationRepository organizationRepository;

    public Organization getOrganizationById(Long id) {
        return organizationRepository.findById(id).orElseThrow(NullPointerException::new);
    }
    public void createOrganization(OrganizationDTO organizationDTO) {
        Organization organization = new Organization();
        organization.setName(organizationDTO.getName());
        organizationDTO.setId(organizationRepository.save(organization).getId());
        organizationDTO.getDirectorData().setOrganizationId(organization.getId());
    }
}
