package com.delivery.mydelivery.database.repositories.accountmanage;

import com.delivery.mydelivery.database.entities.accountmanage.role.Role;
import com.delivery.mydelivery.database.entities.accountmanage.role.RoleEnum;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Role findRoleByName(RoleEnum name);
}
