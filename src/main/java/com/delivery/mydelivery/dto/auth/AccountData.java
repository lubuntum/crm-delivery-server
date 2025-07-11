package com.delivery.mydelivery.dto.auth;

import com.delivery.mydelivery.database.entities.accountmanage.accountstatus.ActiveStatusEnum;
import com.delivery.mydelivery.database.entities.accountmanage.role.RoleEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AccountData {
    private Long id;
    private String chatId;
    private String email;
    private RoleEnum role;
    private String employeeName;
    private String employeeSecondName;
    private String employeePatronymic;
    private String phone;
    private String organizationName;
    private Long organizationId;
    private ActiveStatusEnum accountStatus;
}
