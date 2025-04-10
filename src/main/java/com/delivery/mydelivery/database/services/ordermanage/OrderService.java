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
        //TODO Fix it later, insert id by key of employee who create order
        Organization organization = organizationService.getOrganizationById(1L);

        clientOrder.setAddress(clientOrderDTO.getAddress());
        clientOrder.setComment(clientOrderDTO.getComment());
        clientOrder.setStatus(status);
        clientOrder.setClient(client);
        clientOrder.setOrganization(organization);
        return orderRepository.save(clientOrder).getId();
    }
}
