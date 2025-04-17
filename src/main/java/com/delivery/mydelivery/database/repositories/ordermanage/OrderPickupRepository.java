package com.delivery.mydelivery.database.repositories.ordermanage;

import com.delivery.mydelivery.database.entities.ordermanage.OrderPickup;
import com.delivery.mydelivery.database.projections.OrderPickupProjection;
import com.delivery.mydelivery.dto.ordermanage.OrderPickupDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface OrderPickupRepository extends JpaRepository<OrderPickup, Long> {
    OrderPickupProjection findOrderPickupById(Long id);

    @Query("SELECT new com.delivery.mydelivery.dto.ordermanage.OrderPickupDTO( " +
            "op.id, op.takenAt, op.comment, op.itemsCount, op.clientOrder.id, op.courier.id) " +
            "FROM OrderPickup op " +
            "WHERE op.clientOrder.id=:orderId")
    OrderPickupDTO findOrderPickupByOrderId(@Param("orderId") Long orderId);
}
