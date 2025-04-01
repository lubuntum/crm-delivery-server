package com.delivery.mydelivery.database.services.organization;

import com.delivery.mydelivery.database.repositories.ordanization.OrganizationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrganizationService {
    @Autowired
    OrganizationRepository organizationRepository;
}
