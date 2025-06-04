package com.delivery.mydelivery.database.services.accountmanage;

import com.delivery.mydelivery.database.entities.accountmanage.accountstatus.ActiveStatus;
import com.delivery.mydelivery.database.entities.accountmanage.accountstatus.ActiveStatusEnum;
import com.delivery.mydelivery.database.repositories.accountmanage.ActiveStatusRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AccountStatusService {
    @Autowired
    ActiveStatusRepository activeStatusRepository;

    public ActiveStatus getAccountStatusByName(ActiveStatusEnum name) {
        return activeStatusRepository.findActiveStatusByName(name);
    }
}
