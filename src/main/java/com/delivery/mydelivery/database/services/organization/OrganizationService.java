package com.delivery.mydelivery.database.services.organization;

import com.delivery.mydelivery.database.entities.organization.Organization;
import com.delivery.mydelivery.database.repositories.organization.OrganizationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrganizationService {
    @Autowired
    OrganizationRepository organizationRepository;

    public Organization getOrganizationById(Long id) {
        return organizationRepository.findById(id).orElseThrow(NullPointerException::new);
    }
}
