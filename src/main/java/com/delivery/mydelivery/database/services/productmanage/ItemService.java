package com.delivery.mydelivery.database.services.productmanage;

import com.delivery.mydelivery.database.entities.ordermanage.ClientOrder;
import com.delivery.mydelivery.database.entities.productmanage.Item;
import com.delivery.mydelivery.database.entities.productmanage.Material;
import com.delivery.mydelivery.database.projections.MaterialProjection;
import com.delivery.mydelivery.database.repositories.productmanage.ItemRepository;
import com.delivery.mydelivery.database.services.ordermanage.OrderService;
import com.delivery.mydelivery.dto.productmanage.ItemDTO;
import com.delivery.mydelivery.dto.productmanage.ItemMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ItemService {
    @Autowired
    ItemRepository itemRepository;
    @Autowired
    OrderService orderService;
    @Autowired
    MaterialService materialService;

    public Item createItem(ItemDTO itemDTO) {
        ClientOrder order = orderService.getClientOrderById(itemDTO.getOrderId());
        Material material = materialService.getMaterialById(itemDTO.getMaterialId());
        Item item = ItemMapper.fromDTO(itemDTO);
        item.setMaterial(material);
        item.setOrder(order);
        return itemRepository.save(item);
    }
    public List<ItemDTO> getItemsByOrderId(Long orderId) {
        return itemRepository.findItemsByOrderId(orderId);
    }
    public Boolean changeItemReadyStateById(Long id, Boolean isReady) {
        Item item = itemRepository.findById(id).orElseThrow(NullPointerException::new);
        item.setIsReady(isReady);
        itemRepository.save(item);
        return true;
    }
}
