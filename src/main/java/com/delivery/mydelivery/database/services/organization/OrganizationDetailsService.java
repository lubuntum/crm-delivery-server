package com.delivery.mydelivery.database.services.organization;

import com.delivery.mydelivery.database.entities.organization.OrganizationDetails;
import com.delivery.mydelivery.database.projections.organization.OrganizationDetailsProjection;
import com.delivery.mydelivery.database.repositories.organization.OrganizationDetailsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrganizationDetailsService {
    @Autowired
    OrganizationDetailsRepository organizationDetailsRepository;

    public OrganizationDetailsProjection getOrganizationDetailsByOrganizationId(Long organizationId) {
        return organizationDetailsRepository.findProjectionByOrganizationId(organizationId);
    }
    public void updateOrganizationDetails(OrganizationDetails organizationDetails) {
        OrganizationDetails existingOrganizationDetails = organizationDetailsRepository
                .findByOrganizationId(organizationDetails.getOrganization().getId());
        if (existingOrganizationDetails != null) {
            existingOrganizationDetails.setBrandName(organizationDetails.getBrandName());
            existingOrganizationDetails.setInn(organizationDetails.getInn());
            existingOrganizationDetails.setKpp(organizationDetails.getKpp());
            existingOrganizationDetails.setOgrn(organizationDetails.getOgrn());
            existingOrganizationDetails.setLegalAddress(organizationDetails.getLegalAddress());
            existingOrganizationDetails.setCurrentAccount(organizationDetails.getCurrentAccount());
            existingOrganizationDetails.setBankName(organizationDetails.getBankName());
            existingOrganizationDetails.setCorrespondAccount(organizationDetails.getCorrespondAccount());
            existingOrganizationDetails.setBic(organizationDetails.getBic());
            existingOrganizationDetails.setPhoneNumber(organizationDetails.getPhoneNumber());
            existingOrganizationDetails.setEmailAddress(organizationDetails.getEmailAddress());
            organizationDetailsRepository.save(existingOrganizationDetails);
            return;
        }
        organizationDetailsRepository.save(organizationDetails);
    }
}
