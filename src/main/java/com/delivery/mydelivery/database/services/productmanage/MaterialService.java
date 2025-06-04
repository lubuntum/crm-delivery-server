package com.delivery.mydelivery.database.services.productmanage;

import com.delivery.mydelivery.database.entities.organization.Organization;
import com.delivery.mydelivery.database.entities.productmanage.Material;
import com.delivery.mydelivery.database.projections.MaterialProjection;
import com.delivery.mydelivery.database.repositories.productmanage.MaterialRepository;
import com.delivery.mydelivery.database.services.organization.OrganizationService;
import com.delivery.mydelivery.dto.productmanage.MaterialDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MaterialService {
    @Autowired
    private MaterialRepository materialRepository;
    @Autowired
    private OrganizationService organizationService;

    public List<MaterialProjection> getMaterialsByOrganizationId(Long organizationId) {
        return materialRepository.findByOrganizationId(organizationId);
    }
    public Material getMaterialById(Long id) {
        return materialRepository.findById(id).orElseThrow(NullPointerException::new);
    }
    public Long createMaterial(MaterialDTO materialDTO) {
        Organization organization = organizationService.getOrganizationById(materialDTO.getOrganizationId());
        Material material = new Material();
        material.setName(materialDTO.getName());
        material.setOrganization(organization);
        return materialRepository.save(material).getId();
    }
    public boolean removeMaterial(MaterialDTO materialDTO) {
        Material material = materialRepository.findMaterialEntityById(materialDTO.getId());
        materialRepository.delete(material);
        return true;
    }
}
