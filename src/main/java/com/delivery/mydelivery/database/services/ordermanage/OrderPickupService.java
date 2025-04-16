package com.delivery.mydelivery.database.services.ordermanage;

import com.delivery.mydelivery.database.entities.accountmanage.Employee;
import com.delivery.mydelivery.database.entities.ordermanage.ClientOrder;
import com.delivery.mydelivery.database.entities.ordermanage.OrderPickup;
import com.delivery.mydelivery.database.projections.OrderPickupProjection;
import com.delivery.mydelivery.database.repositories.ordermanage.OrderPickupRepository;
import com.delivery.mydelivery.database.services.accountmanage.EmployeeService;
import com.delivery.mydelivery.dto.ordermanage.OrderPickupDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class OrderPickupService {
    @Autowired
    OrderPickupRepository orderPickupRepository;
    @Autowired
    OrderService orderService;
    @Autowired
    EmployeeService employeeService;
    public OrderPickupProjection getOrderPickupById(Long id) {
        return orderPickupRepository.findOrderPickupById(id);
    }
    @Transactional
    public Long createOrderPickup(OrderPickupDTO orderPickupDTO) {
        OrderPickup orderPickup = new OrderPickup();
        ClientOrder clientOrder = orderService.getClientOrderById(orderPickupDTO.getOrderId());
        Employee employee = employeeService.getEmployeeById(orderPickupDTO.getCourierId());

        orderPickup.setComment(orderPickupDTO.getComment());
        orderPickup.setItemsCount(orderPickupDTO.getItemsCount());
        orderPickup.setClientOrder(clientOrder);
        orderPickup.setEmployee(employee);
        return orderPickupRepository.save(orderPickup).getId();
    }
}
