package com.delivery.mydelivery.database.repositories.ordermanage;

import com.delivery.mydelivery.database.entities.ordermanage.OrderInspection;
import com.delivery.mydelivery.dto.ordermanage.OrderInspectionDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface OrderInspectionRepository extends JpaRepository<OrderInspection, Long> {

    @Query("SELECT new com.delivery.mydelivery.dto.ordermanage.OrderInspectionDTO(  " +
            "oi.id, oi.clientOrder.id, oi.employee.id, oi.itemsCount, oi.inspectedAt ) " +
            "FROM OrderInspection oi " +
            "WHERE oi.clientOrder.id=:orderId")
    OrderInspectionDTO findOrderInspectionByOrderId(@Param("orderId") Long orderId);
}
