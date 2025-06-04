package com.delivery.mydelivery.dto.organization;

import com.delivery.mydelivery.database.entities.accountmanage.accountstatus.ActiveStatusEnum;
import com.delivery.mydelivery.dto.auth.AccountData;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrganizationDTO {
    private Long id;
    private String name;
    private String createdAt;
    private ActiveStatusEnum activeStatus;
    private AccountData directorData;
}
