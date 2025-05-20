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
    public List<Role> getAllRoles(){
        return roleRepository.findAll();
    }

    public List<Role> getRoles(){
        return roleRepository.findAll();
    }
    public Role getRoleByName(RoleEnum name) {
        return roleRepository.findRoleByName(name);
    }
}
