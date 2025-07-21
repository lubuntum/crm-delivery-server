package com.delivery.mydelivery.database.repositories.organization;

import com.delivery.mydelivery.database.entities.organization.OrganizationDetails;
import com.delivery.mydelivery.database.projections.organization.OrganizationDetailsProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface OrganizationDetailsRepository extends JpaRepository<OrganizationDetails, Long> {
    @Query("SELECT od from OrganizationDetails od WHERE od.organization.id = :organizationId")
    OrganizationDetailsProjection findProjectionByOrganizationId(Long organizationId);
    @Query("SELECT od from OrganizationDetails od WHERE od.organization.id = :organizationId")
    OrganizationDetails findByOrganizationId(Long organizationId);

}
