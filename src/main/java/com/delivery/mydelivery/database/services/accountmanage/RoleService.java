package com.delivery.mydelivery.database.services.accountmanage;

import com.delivery.mydelivery.database.entities.accountmanage.Account;
import com.delivery.mydelivery.database.entities.accountmanage.role.Role;
import com.delivery.mydelivery.database.entities.accountmanage.role.RoleEnum;
import com.delivery.mydelivery.database.repositories.accountmanage.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoleService {
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private AccountService accountService;
    public List<Role> getAllRoles(){
        return roleRepository.findAll();
    }
    public boolean checkRoleForAccountId(RoleEnum roleName, Long accountId) {
        Role role = roleRepository.findRoleByName(roleName);
        if (role == null)
            throw new IllegalArgumentException("Role not found: " + roleName);
        Account account = accountService.getAccountById(accountId);
        return account != null && account.getRole().getId().equals(role.getId());
    }
}
