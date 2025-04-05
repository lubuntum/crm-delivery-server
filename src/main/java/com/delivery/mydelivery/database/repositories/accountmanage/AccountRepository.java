package com.delivery.mydelivery.database.repositories.accountmanage;

import com.delivery.mydelivery.database.entities.accountmanage.Account;
import com.delivery.mydelivery.dto.auth.AccountData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface AccountRepository extends JpaRepository<Account, Long> {
    Account findByEmail(String email);

    @Query("SELECT new com.delivery.mydelivery.dto.auth.AccountData(" +
            "a.id, a.email, r.name, " +
            "e.name, e.secondName, e.patronymic, " +
            "e.phone, o.name) " +
            "FROM Account a " +
            "INNER JOIN role r " +
            "INNER JOIN employee e " +
            "INNER JOIN organization o " +
            "WHERE a.id = :id")
    AccountData findAccountDataById(Long id);
}
