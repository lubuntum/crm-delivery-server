package com.delivery.mydelivery.dto.productmanage;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ItemDTO {
    Long id;
    Long materialId;
    String materialName;
    Long orderId;
    BigDecimal price;
    BigDecimal pricePerUnit;
    BigDecimal additionalPrice;
    Double size;
    Double width;
    Double height;
    String comment;

    Boolean isReady;

}
