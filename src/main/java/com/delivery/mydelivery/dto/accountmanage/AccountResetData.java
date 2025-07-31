package com.delivery.mydelivery.dto.accountmanage;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AccountResetData {
    private Long accountId;
    private String password;
    private String email;
}
