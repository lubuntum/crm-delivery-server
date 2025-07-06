package com.delivery.mydelivery.dto.ordermanage.clientorder;

import com.delivery.mydelivery.database.entities.ordermanage.ClientOrder;

public class ClientOrderMapper {
    public static ClientOrderDTO toDTO(ClientOrder c) {
        return new ClientOrderDTO(
                c.getId(), c.getSerialNumber(), c.getAddress(), c.getComment(), c.getCreatedAt(),
                c.getStatus().getName(), c.getTotalPrice(),
                c.getClient().getId(), c.getClient().getName(), c.getClient().getSecondName(), c.getClient().getPatronymic(),
                c.getClient().getEmail(), c.getClient().getPhone(), c.getOrganization().getId(), c.getItems().size()
        );
    }
}
