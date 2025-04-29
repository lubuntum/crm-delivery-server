package com.delivery.mydelivery.database.services.ordermanage;

import com.delivery.mydelivery.database.entities.accountmanage.Employee;
import com.delivery.mydelivery.database.entities.ordermanage.ClientOrder;
import com.delivery.mydelivery.database.entities.ordermanage.OrderInspection;
import com.delivery.mydelivery.database.entities.ordermanage.OrderPickup;
import com.delivery.mydelivery.database.repositories.ordermanage.OrderInspectionRepository;
import com.delivery.mydelivery.database.services.accountmanage.EmployeeService;
import com.delivery.mydelivery.dto.ordermanage.OrderInspectionDTO;
import jakarta.persistence.EntityExistsException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrderInspectionService {
    @Autowired
    private OrderInspectionRepository orderInspectionRepository;
    @Autowired
    private OrderService orderService;
    @Autowired
    private EmployeeService employeeService;
    public Long createOrderInspection(OrderInspectionDTO orderInspectionDTO) {
        OrderInspectionDTO orderInspectionFromDB = orderInspectionRepository.findOrderInspectionByOrderId(orderInspectionDTO.getOrderId());

        if (orderInspectionFromDB != null)
            throw new EntityExistsException("OrderInspection already exists for order with id " + orderInspectionDTO.getOrderId());

        OrderInspection orderInspection = new OrderInspection();
        ClientOrder clientOrder = orderService.getClientOrderById(orderInspectionDTO.getOrderId());
        Employee employee = employeeService.getEmployeeById(orderInspectionDTO.getInspectorId());

        orderInspection.setClientOrder(clientOrder);
        orderInspection.setEmployee(employee);
        orderInspection.setItemsCount(orderInspectionDTO.getItemsCount());

        return orderInspectionRepository.save(orderInspection).getId();

    }

    public OrderInspectionDTO getOrderInspectionDTO(Long orderId) {
        return orderInspectionRepository.findOrderInspectionByOrderId(orderId);
    }
}
