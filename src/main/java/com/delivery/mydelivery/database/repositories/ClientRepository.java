package com.delivery.mydelivery.database.repositories;

import com.delivery.mydelivery.database.entities.accountmanage.Client;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClientRepository extends JpaRepository<Client, Long> {
}
