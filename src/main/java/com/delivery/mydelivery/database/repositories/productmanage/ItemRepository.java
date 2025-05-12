package com.delivery.mydelivery.database.repositories.productmanage;

import com.delivery.mydelivery.database.entities.productmanage.Item;
import com.delivery.mydelivery.dto.productmanage.ItemDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ItemRepository extends JpaRepository<Item, Long> {
    @Query("SELECT new com.delivery.mydelivery.dto.productmanage.ItemDTO( " +
            "i.id, m.id, m.name, o.id, i.price, i.pricePerUnit, i.additionalPrice, " +
            "i.size, i.width, i.height, i.comment, i.isReady, null, null) " +
            "FROM Item i " +
            "JOIN i.material m " +
            "JOIN i.order o " +
            "WHERE o.id=:orderId")
    List<ItemDTO> findItemsByOrderId(Long orderId);
}
