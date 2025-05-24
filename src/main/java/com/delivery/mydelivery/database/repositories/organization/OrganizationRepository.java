package com.delivery.mydelivery.database.repositories.organization;

import com.delivery.mydelivery.database.entities.organization.Organization;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrganizationRepository extends JpaRepository<Organization, Long> {
}
