package com.delivery.mydelivery.database.services.ordermanage;

import com.delivery.mydelivery.database.entities.accountmanage.Client;
import com.delivery.mydelivery.database.entities.ordermanage.ClientOrder;
import com.delivery.mydelivery.database.entities.ordermanage.status.Status;
import com.delivery.mydelivery.database.entities.ordermanage.status.StatusEnum;
import com.delivery.mydelivery.database.entities.organization.Organization;
import com.delivery.mydelivery.database.projections.ordermanage.OrdersTotalStats;
import com.delivery.mydelivery.database.repositories.ordermanage.OrderRepository;
import com.delivery.mydelivery.database.services.accountmanage.ClientService;
import com.delivery.mydelivery.database.services.organization.OrganizationService;
import com.delivery.mydelivery.dto.ordermanage.clientorder.ClientOrderDTO;
import com.delivery.mydelivery.dto.ordermanage.OrderStatusDTO;
import com.delivery.mydelivery.dto.ordermanage.clientorder.ClientOrderMapper;
import com.delivery.mydelivery.utility.serialnumber.SerialNumberFormatter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

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
    public List<ClientOrderDTO> getOrdersBetweenDatesForOrganization(LocalDateTime startDate, LocalDateTime endDate, Long organizationId) {
        Organization organization = organizationService.getOrganizationById(organizationId);
        List<ClientOrder> clientOrders = orderRepository.findByCreatedAtBetweenAndOrganization(startDate, endDate, organization);
        return clientOrders.stream().map(ClientOrderMapper::toDTO).collect(Collectors.toList());

    }
    @Transactional
    public ClientOrder createOrder(ClientOrderDTO clientOrderDTO){
        ClientOrder clientOrder = new ClientOrder();
        Status status = clientOrderDTO.getStatus() != null ?
                statusService.getStatusByName(clientOrderDTO.getStatus()) :
                statusService.getStatusByName(StatusEnum.CREATED);
        //Status status = statusService.getStatusByName(StatusEnum.CREATED);
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
        return orderRepository.save(clientOrder);
    }
    @Transactional
    public ClientOrderDTO updateOrder(ClientOrderDTO clientOrderDTO) {
        ClientOrder clientOrder = orderRepository.findById(clientOrderDTO.getId()).orElseThrow(NullPointerException::new);
        Client client = clientService.getClientById(clientOrderDTO.getClientId());

        clientOrder.setAddress(clientOrderDTO.getAddress());
        clientOrder.setComment(clientOrderDTO.getComment());

        client.setName(clientOrderDTO.getClientName());
        client.setSecondName(clientOrderDTO.getClientSecondName());
        client.setPatronymic(clientOrderDTO.getClientPatronymic());
        client.setEmail(clientOrderDTO.getClientEmail());
        client.setPhone(clientOrderDTO.getClientPhone());

        orderRepository.save(clientOrder);
        clientService.updateClient(client);
        return clientOrderDTO;
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
    /**
     * Return OrdersTotalStats with total price and size of orders (exclude COMPLETE status) for organization_id
     * */
    public OrdersTotalStats getRemainOrdersStatsByOrganizationId(Long organizationId) {
        return orderRepository.findRemainOrdersStatsByOrganization(organizationId, LocalDateTime.now().minusDays(30));
    }
}
