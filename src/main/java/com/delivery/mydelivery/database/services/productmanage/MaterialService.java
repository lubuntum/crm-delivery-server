package com.delivery.mydelivery.database.services.productmanage;

import com.delivery.mydelivery.database.entities.productmanage.Material;
import com.delivery.mydelivery.database.projections.MaterialProjection;
import com.delivery.mydelivery.database.repositories.productmanage.MaterialRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class MaterialService {
    @Autowired
    private MaterialRepository materialRepository;

    public List<MaterialProjection> getMaterialsByOrganizationId(Long organizationId) {
        return materialRepository.findByOrganizationId(organizationId);
    }
    public Material getMaterialById(Long id) {
        return materialRepository.findById(id).orElseThrow(NullPointerException::new);
    }
}
