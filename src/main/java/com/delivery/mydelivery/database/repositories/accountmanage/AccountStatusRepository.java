package com.delivery.mydelivery.database.repositories.accountmanage;

import com.delivery.mydelivery.database.entities.accountmanage.Account;
import com.delivery.mydelivery.database.entities.accountmanage.accountstatus.AccountStatus;
import com.delivery.mydelivery.database.entities.accountmanage.accountstatus.AccountStatusEnum;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountStatusRepository extends JpaRepository<com.delivery.mydelivery.database.entities.accountmanage.accountstatus.AccountStatus, Long> {
    AccountStatus findAccountStatusByName(AccountStatusEnum name);
}
