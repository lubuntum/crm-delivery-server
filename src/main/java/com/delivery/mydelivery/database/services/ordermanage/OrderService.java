package com.delivery.mydelivery.database.services.ordermanage;

import com.delivery.mydelivery.database.entities.accountmanage.Client;
import com.delivery.mydelivery.database.entities.ordermanage.ClientOrder;
import com.delivery.mydelivery.database.entities.ordermanage.status.Status;
import com.delivery.mydelivery.database.entities.ordermanage.status.StatusEnum;
import com.delivery.mydelivery.database.entities.organization.Organization;
import com.delivery.mydelivery.database.repositories.ordermanage.OrderRepository;
import com.delivery.mydelivery.database.services.accountmanage.ClientService;
import com.delivery.mydelivery.database.services.organization.OrganizationService;
import com.delivery.mydelivery.dto.ordermanage.ClientOrderDTO;
import com.delivery.mydelivery.dto.ordermanage.OrderStatusDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class OrderService {
    @Autowired
    OrderRepository orderRepository;
    @Autowired
    StatusService statusService;
    @Autowired
    ClientService clientService;
    @Autowired
    OrganizationService organizationService;

    public List<ClientOrderDTO> getAllOrdersByOrganizationId(Long organizationId) {
        return orderRepository.findByOrganizationId(organizationId);
    }
    @Transactional
    public Long createOrder(ClientOrderDTO clientOrderDTO){
        ClientOrder clientOrder = new ClientOrder();
        Status status = statusService.getStatusByName(StatusEnum.CREATED);
        Client client = clientService.getClientById(clientOrderDTO.getClientId());
        Organization organization = organizationService.getOrganizationById(clientOrderDTO.getOrganizationId());

        clientOrder.setAddress(clientOrderDTO.getAddress());
        clientOrder.setComment(clientOrderDTO.getComment());
        clientOrder.setStatus(status);
        clientOrder.setClient(client);
        clientOrder.setOrganization(organization);
        return orderRepository.save(clientOrder).getId();
    }
    public StatusEnum changeOrderStatus(OrderStatusDTO orderStatusDTO) {
        ClientOrder order = orderRepository.findById(orderStatusDTO.getOrderId())
                .orElseThrow(NullPointerException::new);
        Status status = statusService.getStatusByName(orderStatusDTO.getStatus());
        order.setStatus(status);
        return orderRepository.save(order).getStatus().getName();
    }
    public ClientOrderDTO getClientOrderDTOById(Long id) {
        return orderRepository.findDTOById(id);
    }
    public ClientOrder getClientOrderById(Long id) {
        return orderRepository.findById(id).orElseThrow(NullPointerException::new);
    }
}
