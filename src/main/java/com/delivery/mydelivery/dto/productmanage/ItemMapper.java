package com.delivery.mydelivery.dto.productmanage;

import com.delivery.mydelivery.database.entities.productmanage.Item;

public class ItemMapper {
    public static ItemDTO toDTO(Item item) {
        ItemDTO itemDTO = new ItemDTO();
        itemDTO.setId(item.getId());

        itemDTO.setSize(item.getSize());
        itemDTO.setHeight(item.getHeight());
        itemDTO.setWidth(item.getWidth());

        itemDTO.setPrice(item.getPrice());
        itemDTO.setPricePerUnit(item.getPricePerUnit());
        itemDTO.setAdditionalPrice(item.getAdditionalPrice());

        itemDTO.setComment(item.getComment());

        itemDTO.setIsReady(item.getIsReady());

        itemDTO.setMaterialId(item.getMaterial().getId());
        itemDTO.setMaterialName(item.getMaterial().getName());
        itemDTO.setOrderId(item.getOrder().getId());

        itemDTO.setOrderImages(item.getOrderImages());
        return itemDTO;
    }
    public static Item fromDTO(ItemDTO itemDTO) {
        Item item = new Item();
        item.setSize(itemDTO.getSize());
        item.setWidth(itemDTO.getWidth());
        item.setHeight(itemDTO.getHeight());
        item.setPrice(itemDTO.getPrice());
        item.setPricePerUnit(itemDTO.getPricePerUnit());
        item.setAdditionalPrice(itemDTO.getAdditionalPrice());
        item.setComment(itemDTO.getComment());
        item.setIsReady(itemDTO.getIsReady());
        return item;
    }
}
