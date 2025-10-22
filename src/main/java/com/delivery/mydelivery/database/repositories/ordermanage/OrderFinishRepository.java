package com.delivery.mydelivery.database.repositories.ordermanage;

import com.delivery.mydelivery.database.entities.ordermanage.OrderFinish;
import com.delivery.mydelivery.dto.ordermanage.OrderFinishDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface OrderFinishRepository extends JpaRepository<OrderFinish, Long> {
    @Query("SELECT new com.delivery.mydelivery.dto.ordermanage.OrderFinishDTO( " +
            "of.id, of.clientOrder.id, of.employee.id, of.comment, " +
            "of.itemsCount, of.deliveredAt, of.paymentMethod.name, null, of.tips, of.deliveryPrice, of.completionUrl, of.discount) " +
            "FROM OrderFinish of " +
            "WHERE of.clientOrder.id=:orderId")
    OrderFinishDTO findOrderFinishByOrderId(@Param("orderId") Long orderId);
    @Query("SELECT new com.delivery.mydelivery.dto.ordermanage.OrderFinishDTO( " +
            "of.id, of.clientOrder.id, of.employee.id, of.comment, " +
            "of.itemsCount, of.deliveredAt, of.paymentMethod.name, null, of.tips, of.deliveryPrice, of.completionUrl, of.discount) " +
            "FROM OrderFinish of WHERE of.clientOrder.id in :ordersId")
    List<OrderFinishDTO> findDTOsByOrderIdIn(@Param("ordersId") List<Long> ordersId);
}
