package com.delivery.mydelivery.database.services.ordermanage;

import com.delivery.mydelivery.database.entities.accountmanage.Employee;
import com.delivery.mydelivery.database.entities.accountmanage.EmployeeWorkflow;
import com.delivery.mydelivery.database.entities.ordermanage.ClientOrder;
import com.delivery.mydelivery.database.entities.ordermanage.OrderFinish;
import com.delivery.mydelivery.database.entities.ordermanage.payment.PaymentMethod;
import com.delivery.mydelivery.database.repositories.ordermanage.OrderFinishRepository;
import com.delivery.mydelivery.database.services.accountmanage.EmployeeService;
import com.delivery.mydelivery.database.services.accountmanage.EmployeeWorkflowService;
import com.delivery.mydelivery.database.services.ordermanage.payment.PaymentMethodService;
import com.delivery.mydelivery.dto.ordermanage.OrderFinishDTO;
import jakarta.persistence.EntityExistsException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class OrderFinishService {
    @Autowired
    OrderFinishRepository orderFinishRepository;
    @Autowired
    OrderService orderService;
    @Autowired
    EmployeeService employeeService;
    @Autowired
    PaymentMethodService paymentMethodService;
    @Autowired
    EmployeeWorkflowService employeeWorkflowService;
    @Transactional
    public Long createOrderFinish(OrderFinishDTO orderFinishDTO) {
        OrderFinishDTO orderFinishDTOFromDB = orderFinishRepository.findOrderFinishByOrderId(orderFinishDTO.getOrderId());
        if (orderFinishDTOFromDB != null)
            throw new EntityExistsException("OrderFinish already exists for order with id " + orderFinishDTO.getOrderId());
        ClientOrder clientOrder = orderService.getClientOrderById(orderFinishDTO.getOrderId());
        Employee employee = employeeService.getEmployeeById(orderFinishDTO.getCourierId());
        PaymentMethod paymentMethod = paymentMethodService.getPaymentMethodByName(orderFinishDTO.getPaymentMethod());

        OrderFinish orderFinish = new OrderFinish();
        orderFinish.setComment(orderFinishDTO.getComment());
        orderFinish.setItemsCount(orderFinishDTO.getItemsCount());
        orderFinish.setPaymentMethod(paymentMethod);
        orderFinish.setClientOrder(clientOrder);
        orderFinish.setEmployee(employee);
        orderFinish.setId(orderFinishRepository.save(orderFinish).getId());
        employeeWorkflowService.updateEmployeeWorkflow(orderFinishDTO);
        return orderFinish.getId();
    }
    public OrderFinishDTO getOrderFinishByOrderId(Long orderId) {
        return orderFinishRepository.findOrderFinishByOrderId(orderId);
    }
}
