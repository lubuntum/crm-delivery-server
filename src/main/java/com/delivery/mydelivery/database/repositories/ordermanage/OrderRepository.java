package com.delivery.mydelivery.database.repositories.ordermanage;

import com.delivery.mydelivery.database.entities.ordermanage.ClientOrder;
import com.delivery.mydelivery.dto.ordermanage.ClientOrderDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface OrderRepository extends JpaRepository<ClientOrder, Long> {
    @Query("SELECT new com.delivery.mydelivery.dto.ordermanage.ClientOrderDTO( " +
            "co.id, co.address, co.comment, co.createdAt, co.status.name, " +
            "c.id ,c.name, c.secondName, c.patronymic, c.email, c.phone, co.organization.id ) " +
            "FROM ClientOrder co " +
            "JOIN co.client c " +
            "WHERE co.organization.id=:organizationId")
    List<ClientOrderDTO> findByOrganizationId(@Param("organizationId") Long organizationId);
    @Query("SELECT new com.delivery.mydelivery.dto.ordermanage.ClientOrderDTO( " +
            "co.id, co.address, co.comment, co.createdAt, co.status.name, " +
            "c.id ,c.name, c.secondName, c.patronymic, c.email, c.phone, co.organization.id ) " +
            "FROM ClientOrder co " +
            "JOIN co.client c " +
            "WHERE co.id=:id")
    ClientOrderDTO findDTOById(Long id);
}
