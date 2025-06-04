package com.delivery.mydelivery.dto.productmanage;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MaterialDTO {
    Long id;
    Long organizationId;
    String name;
}
