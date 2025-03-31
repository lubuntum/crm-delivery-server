package com.delivery.mydelivery.database.repositories;

import com.delivery.mydelivery.database.entities.accountmanage.Account;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRepository extends JpaRepository<Account, Long> {
}
