package com.delivery.mydelivery.database.repositories.ordermanage;

import com.delivery.mydelivery.database.entities.ordermanage.OrderPickup;
import com.delivery.mydelivery.database.projections.OrderPickupProjection;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderPickupRepository extends JpaRepository<OrderPickup, Long> {
    OrderPickupProjection findOrderPickupById(Long id);
}
