package com.delivery.mydelivery.dto.ordermanage.clientorder;

import com.delivery.mydelivery.database.entities.ordermanage.status.StatusEnum;
import com.delivery.mydelivery.database.entities.productmanage.Item;

import com.delivery.mydelivery.dto.productmanage.ItemDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ClientOrderWithItemsDTO {
    private Long id;
    private String serialNumber;
    private String address;
    private String comment;
    private LocalDateTime createdAt;
    private StatusEnum status;
    private BigDecimal totalPrice;
    private Long clientId;
    private String clientName;
    private String clientSecondName;
    private String clientPatronymic;
    private String clientEmail;
    private String clientPhone;
    private Long organizationId;
    private Integer itemsCount;
    private List<ItemDTO> items;
}
