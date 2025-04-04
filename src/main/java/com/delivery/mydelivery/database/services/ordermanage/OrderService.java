package com.delivery.mydelivery.database.services.ordermanage;

import com.delivery.mydelivery.database.entities.ordermanage.ClientOrder;
import com.delivery.mydelivery.database.repositories.ordermanage.OrderRepository;
import com.delivery.mydelivery.dto.ordermanage.ClientOrderDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderService {
    @Autowired
    OrderRepository orderRepository;

    public List<ClientOrderDTO> getAllOrdersByOrganizationId(Long organizationId) {
        return orderRepository.findByOrganizationId(organizationId);
    };
}
