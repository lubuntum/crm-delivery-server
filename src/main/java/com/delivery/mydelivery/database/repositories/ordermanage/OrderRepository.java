package com.delivery.mydelivery.database.repositories.ordermanage;

import com.delivery.mydelivery.database.entities.ordermanage.ClientOrder;
import com.delivery.mydelivery.database.projections.ordermanage.OrdersTotalStats;
import com.delivery.mydelivery.dto.ordermanage.ClientOrderDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface OrderRepository extends JpaRepository<ClientOrder, Long> {
    @Query("SELECT new com.delivery.mydelivery.dto.ordermanage.ClientOrderDTO( " +
            "co.id, co.serialNumber, co.address, co.comment, co.createdAt, co.status.name, co.totalPrice, " +
            "c.id ,c.name, c.secondName, c.patronymic, c.email, c.phone, co.organization.id ) " +
            "FROM ClientOrder co " +
            "JOIN co.client c " +
            "WHERE co.organization.id=:organizationId " +
            "AND EXTRACT(MONTH from co.createdAt) = EXTRACT(MONTH from CURRENT_DATE) " +
            "AND EXTRACT(YEAR from co.createdAt) = EXTRACT(YEAR from CURRENT_DATE) " +
            "AND co.isActive = true")
    List<ClientOrderDTO> findByOrganizationId(@Param("organizationId") Long organizationId);
    @Query("SELECT new com.delivery.mydelivery.dto.ordermanage.ClientOrderDTO( " +
            "co.id, co.serialNumber, co.address, co.comment, co.createdAt, co.status.name, co.totalPrice, " +
            "c.id ,c.name, c.secondName, c.patronymic, c.email, c.phone, co.organization.id ) " +
            "FROM ClientOrder co " +
            "JOIN co.client c " +
            "WHERE co.organization.id=:organizationId " +
            "AND co.createdAt >= :startedAt " +
            "AND co.isActive = true")
    List<ClientOrderDTO> findByOrganizationIdFromDate(@Param("organizationId") Long organizationId, @Param("startedAt")LocalDateTime startedAt);
    @Query("SELECT new com.delivery.mydelivery.dto.ordermanage.ClientOrderDTO( " +
            "co.id, co.serialNumber, co.address, co.comment, co.createdAt, co.status.name, co.totalPrice, " +
            "c.id ,c.name, c.secondName, c.patronymic, c.email, c.phone, co.organization.id ) " +
            "FROM ClientOrder co " +
            "JOIN co.client c " +
            "WHERE co.id=:id")
    ClientOrderDTO findDTOById(Long id);

    Long countByOrganizationId(Long organizationId);
    @Query("SELECT new com.delivery.mydelivery.database.projections.ordermanage.OrdersTotalStats( " +
            "SUM(i.size), SUM(i.price), COUNT(DISTINCT co)) " +
            "FROM ClientOrder co " +
            "JOIN co.items i " +
            "JOIN co.status s " +
            "WHERE s.name <> 'COMPLETED' AND " +
            "co.organization.id = :organizationId AND " +
            "co.createdAt >= :startedAt")
    OrdersTotalStats findRemainOrdersStatsByOrganization(@Param("organizationId") Long organizationId,
                                                         @Param("startedAt") LocalDateTime startedAt);
}
