package com.delivery.mydelivery.dto.ordermanage.orderpickup;

import com.delivery.mydelivery.database.entities.ordermanage.OrderPickup;

public class OrderPickupMapper {
    public static OrderPickupDTO toDTO(OrderPickup orderPickup) {
        OrderPickupDTO dto = new OrderPickupDTO();
        dto.setId(orderPickup.getId());
        dto.setTakenAt(orderPickup.getTakenAt());
        dto.setComment(orderPickup.getComment());
        dto.setItemsCount(orderPickup.getItemsCount());
        dto.setOrderId(orderPickup.getClientOrder().getId());
        dto.setCourierId(orderPickup.getCourier().getId());
        dto.setOrderImages(orderPickup.getOrderImages());
        return dto;
    }
}
