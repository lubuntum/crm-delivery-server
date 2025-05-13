package com.delivery.mydelivery.database.services.productmanage;

import com.delivery.mydelivery.database.entities.ordermanage.ClientOrder;
import com.delivery.mydelivery.database.entities.productmanage.Item;
import com.delivery.mydelivery.database.entities.productmanage.Material;
import com.delivery.mydelivery.database.projections.MaterialProjection;
import com.delivery.mydelivery.database.repositories.productmanage.ItemRepository;
import com.delivery.mydelivery.database.services.orderimage.OrderImageService;
import com.delivery.mydelivery.database.services.ordermanage.OrderService;
import com.delivery.mydelivery.dto.productmanage.ItemDTO;
import com.delivery.mydelivery.dto.productmanage.ItemMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ItemService {
    @Autowired
    ItemRepository itemRepository;
    @Autowired
    OrderService orderService;
    @Autowired
    MaterialService materialService;
    @Autowired
    OrderImageService orderImageService;

    public Item createItem(ItemDTO itemDTO) {
        ClientOrder order = orderService.getClientOrderById(itemDTO.getOrderId());
        Material material = materialService.getMaterialById(itemDTO.getMaterialId());
        Item item = ItemMapper.fromDTO(itemDTO);
        item.setMaterial(material);
        item.setOrder(order);
        item.setOrderImages(orderImageService.saveImages(itemDTO.getImagesTemp()));
        return itemRepository.save(item);
    }
    //TODO find images or use map if needed
    public List<ItemDTO> getItemsByOrderId(Long orderId) {
        return itemRepository.findItemsByOrderId(orderId)
                .stream()
                .map(ItemMapper::toDTO)
                .toList();
    }
    public Boolean changeItemReadyStateById(Long id, Boolean isReady) {
        Item item = itemRepository.findById(id).orElseThrow(NullPointerException::new);
        item.setIsReady(isReady);
        itemRepository.save(item);
        return true;
    }
}
