package com.delivery.mydelivery.database.repositories.productmanage;

import com.delivery.mydelivery.database.entities.productmanage.Material;
import com.delivery.mydelivery.database.projections.MaterialProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface MaterialRepository extends JpaRepository<Material, Long> {
    List<MaterialProjection> findByOrganizationId(Long organizationId);
    MaterialProjection findMaterialById(Long id);
    Material findMaterialEntityById(Long id);
}
