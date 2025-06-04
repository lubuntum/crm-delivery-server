package com.delivery.mydelivery.database.repositories.accountmanage;

import com.delivery.mydelivery.database.entities.accountmanage.accountstatus.ActiveStatus;
import com.delivery.mydelivery.database.entities.accountmanage.accountstatus.ActiveStatusEnum;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ActiveStatusRepository extends JpaRepository<ActiveStatus, Long> {
    ActiveStatus findActiveStatusByName(ActiveStatusEnum name);
}
