package com.delivery.mydelivery.dto.organization;

import com.delivery.mydelivery.database.entities.accountmanage.Account;
import com.delivery.mydelivery.database.entities.accountmanage.Employee;
import com.delivery.mydelivery.database.entities.accountmanage.role.RoleEnum;
import com.delivery.mydelivery.database.entities.organization.Organization;
import com.delivery.mydelivery.dto.auth.AccountData;

public class OrganizationMapper {
    public static OrganizationDTO toDTO (Organization organization) {
        OrganizationDTO organizationDTO = new OrganizationDTO();
        organizationDTO.setId(organization.getId());
        organizationDTO.setName(organization.getName());
        organizationDTO.setActiveStatus(organization.getOrganizationActiveStatus().getName());
        organizationDTO.setCreatedAt(organizationDTO.getCreatedAt());
        if (organization.getEmployees() == null || organization.getEmployees().size() == 0) return organizationDTO;
        Employee director = organization.getEmployees().stream()
                .filter(e -> e.getAccount() != null
                        && e.getAccount().getRole() != null
                        && e.getAccount().getRole().getName().equals(RoleEnum.DIRECTOR))
                .findFirst().orElse(null);
        if (director == null) return organizationDTO;
        organizationDTO.setDirectorData(getDirectorData(director.getAccount()));
        return organizationDTO;
    }
    private static AccountData getDirectorData(Account account) {
        if (account == null) return null;
        AccountData accountData = new AccountData();
        accountData.setId(account.getId());
        accountData.setEmail(account.getEmail());
        accountData.setPhone(account.getEmployee().getPhone());
        accountData.setEmployeeName(account.getEmployee().getName());
        accountData.setEmployeeSecondName(account.getEmployee().getSecondName());
        accountData.setEmployeePatronymic(account.getEmployee().getPatronymic());
        accountData.setRole(account.getRole().getName());
        return accountData;
    }
}
