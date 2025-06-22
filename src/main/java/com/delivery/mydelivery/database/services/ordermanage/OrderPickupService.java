package com.delivery.mydelivery.database.services.ordermanage;

import com.delivery.mydelivery.database.entities.accountmanage.Employee;
import com.delivery.mydelivery.database.entities.orderimages.OrderImage;
import com.delivery.mydelivery.database.entities.ordermanage.ClientOrder;
import com.delivery.mydelivery.database.entities.ordermanage.OrderPickup;
import com.delivery.mydelivery.database.projections.OrderPickupProjection;
import com.delivery.mydelivery.database.repositories.ordermanage.OrderPickupRepository;
import com.delivery.mydelivery.database.services.accountmanage.EmployeeService;
import com.delivery.mydelivery.database.services.orderimage.OrderImageService;
import com.delivery.mydelivery.dto.ordermanage.orderpickup.OrderPickupDTO;
import com.delivery.mydelivery.dto.ordermanage.orderpickup.OrderPickupMapper;
import jakarta.persistence.EntityExistsException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class OrderPickupService {
    @Autowired
    OrderPickupRepository orderPickupRepository;
    @Autowired
    OrderService orderService;
    @Autowired
    EmployeeService employeeService;
    @Autowired
    OrderImageService orderImageService;
    public OrderPickupProjection getOrderPickupById(Long id) {
        return orderPickupRepository.findOrderPickupById(id);
    }

    public OrderPickupDTO getOrderPickupByOrderId(Long orderId) {
        ClientOrder order = orderService.getClientOrderById(orderId);
        OrderPickup orderPickup = orderPickupRepository.findOrderPickupByClientOrder(order);
        if (orderPickup == null) return null;
        return OrderPickupMapper.toDTO(orderPickup);
    }

    @Transactional
    public Long createOrderPickup(OrderPickupDTO orderPickupDTO) {
        OrderPickupDTO orderPickupFromDB = getOrderPickupByOrderId(orderPickupDTO.getOrderId());
        if (orderPickupFromDB != null)
            throw new EntityExistsException("OrderPickup already exists for order with id " + orderPickupDTO.getOrderId());

        OrderPickup orderPickup = new OrderPickup();
        ClientOrder clientOrder = orderService.getClientOrderById(orderPickupDTO.getOrderId());
        Employee employee = employeeService.getEmployeeById(orderPickupDTO.getCourierId());

        orderPickup.setComment(orderPickupDTO.getComment());
        orderPickup.setItemsCount(orderPickupDTO.getItemsCount());
        orderPickup.setClientOrder(clientOrder);
        orderPickup.setCourier(employee);
        orderPickup.setOrderImages(orderImageService.saveImages(orderPickupDTO.getImagesTemp()));
        return orderPickupRepository.save(orderPickup).getId();
    }
    public OrderPickupDTO updateOrderPickup(OrderPickupDTO orderPickupDTO) {
        OrderPickup orderPickup = orderPickupRepository.findById(orderPickupDTO.getId()).orElseThrow(NullPointerException::new);
        orderPickup.setItemsCount(orderPickupDTO.getItemsCount());
        orderPickup.setComment(orderPickupDTO.getComment());
        //if was added some additional images then save them and add to pickupOrder
        if (orderPickupDTO.getImagesTemp() != null && orderPickupDTO.getImagesTemp().size() > 0){
            List<OrderImage> orderImages = orderImageService.saveImages(orderPickupDTO.getImagesTemp());
            orderPickup.getOrderImages().addAll(orderImages);
        }
        orderPickupRepository.save(orderPickup);
        return orderPickupDTO;
    }
}
