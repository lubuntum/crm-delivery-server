package com.delivery.mydelivery.database.services.accountmanage;

import com.delivery.mydelivery.database.entities.accountmanage.accountstatus.AccountStatus;
import com.delivery.mydelivery.database.entities.accountmanage.accountstatus.AccountStatusEnum;
import com.delivery.mydelivery.database.repositories.accountmanage.AccountStatusRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AccountStatusService {
    @Autowired
    AccountStatusRepository accountStatusRepository;

    public AccountStatus getAccountStatusByName(AccountStatusEnum name) {
        return accountStatusRepository.findAccountStatusByName(name);
    }
}
