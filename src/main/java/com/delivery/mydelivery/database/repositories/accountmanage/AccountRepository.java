package com.delivery.mydelivery.database.repositories.accountmanage;

import com.delivery.mydelivery.database.entities.accountmanage.Account;
import com.delivery.mydelivery.dto.auth.AccountData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface AccountRepository extends JpaRepository<Account, Long> {
    Account findByEmail(String email);

    @Query("SELECT new com.delivery.mydelivery.dto.auth.AccountData(" +
            "a.id, e.chatId, a.email, r.name, " +
            "e.name, e.secondName, e.patronymic, " +
            "e.phone, o.name, o.id, ac.name) " +
            "FROM Account a " +
            "JOIN role r " +
            "JOIN employee e " +
            "JOIN organization o " +
            "JOIN activeStatus ac " +
            "WHERE a.id = :id")
    AccountData findAccountDataById(Long id);

    @Query("SELECT new com.delivery.mydelivery.dto.auth.AccountData(" +
            "a.id, e.chatId, a.email, r.name, " +
            "e.name, e.secondName, e.patronymic, " +
            "e.phone, o.name, o.id, ac.name) " +
            "FROM Account a " +
            "JOIN role r " +
            "JOIN employee e " +
            "JOIN organization o " +
            "JOIN activeStatus ac " +
            "WHERE e.organization.id = :organizationId AND ac.name<>DELETED")
    List<AccountData> findAccountDataByOrganizationId(Long organizationId);
}
