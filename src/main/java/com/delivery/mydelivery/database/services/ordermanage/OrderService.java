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
import com.delivery.mydelivery.utility.serialnumber.SerialNumberFormatter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
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
        LocalDateTime startedAt = LocalDateTime.now().minusDays(30);
        return orderRepository.findByOrganizationIdFromDate(organizationId, startedAt);
    }
    public Long getTotalCountByOrganizationId(Long organizationId) {
        return orderRepository.countByOrganizationId(organizationId);
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
        clientOrder.setTotalPrice(clientOrderDTO.getTotalPrice());
        clientOrder.setIsActive(true);

        clientOrder.setClient(client);
        clientOrder.setOrganization(organization);

        clientOrder.setId(orderRepository.save(clientOrder).getId());

        clientOrder.setSerialNumber(SerialNumberFormatter.calcSerialNumber(getTotalCountByOrganizationId(organization.getId())));
        return orderRepository.save(clientOrder).getId();
    }
    public void removeOrder(ClientOrderDTO clientOrderDTO) {
        ClientOrder clientOrder = orderRepository.findById(clientOrderDTO.getId()).orElseThrow(NullPointerException::new);
        if (!clientOrder.getStatus().getName().equals(StatusEnum.CREATED))
            throw new IllegalArgumentException("Only for orders with status CREATED");
        //orderRepository.delete(clientOrder);
        clientOrder.setIsActive(false);
        orderRepository.save(clientOrder);
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
