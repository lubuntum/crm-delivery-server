package com.delivery.mydelivery.database.repositories;

import com.delivery.mydelivery.database.entities.accountmanage.role.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Long> {
}
