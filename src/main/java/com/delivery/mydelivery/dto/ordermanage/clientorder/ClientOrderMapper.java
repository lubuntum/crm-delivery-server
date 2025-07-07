package com.delivery.mydelivery.dto.ordermanage.clientorder;

import com.delivery.mydelivery.database.entities.ordermanage.ClientOrder;
import com.delivery.mydelivery.database.entities.productmanage.Item;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;

public class ClientOrderMapper {
    public static ClientOrderDTO toDTO(ClientOrder c) {
        return new ClientOrderDTO(
                c.getId(), c.getSerialNumber(), c.getAddress(), c.getComment(), c.getCreatedAt(),
                c.getStatus().getName(), calcTotalPriceForOrder(c),
                c.getClient().getId(), c.getClient().getName(), c.getClient().getSecondName(), c.getClient().getPatronymic(),
                c.getClient().getEmail(), c.getClient().getPhone(), c.getOrganization().getId(), c.getItems().size()
        );
    }
    private static BigDecimal calcTotalPriceForOrder(ClientOrder c) {
        return c.getItems().stream().map(Item::getPrice).reduce(BigDecimal.ZERO, BigDecimal::add);
    }
}
