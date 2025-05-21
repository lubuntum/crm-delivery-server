package com.delivery.mydelivery.database.services.accountmanage;

import com.delivery.mydelivery.database.entities.accountmanage.Account;
import com.delivery.mydelivery.database.entities.accountmanage.Employee;
import com.delivery.mydelivery.database.entities.accountmanage.accountstatus.AccountStatus;
import com.delivery.mydelivery.database.entities.accountmanage.accountstatus.AccountStatusEnum;
import com.delivery.mydelivery.database.entities.accountmanage.role.Role;
import com.delivery.mydelivery.database.entities.accountmanage.role.RoleEnum;
import com.delivery.mydelivery.database.entities.organization.Organization;
import com.delivery.mydelivery.database.repositories.accountmanage.AccountRepository;
import com.delivery.mydelivery.database.services.organization.OrganizationService;
import com.delivery.mydelivery.dto.auth.AccountData;
import com.delivery.mydelivery.dto.auth.AuthCredential;
import com.delivery.mydelivery.services.jwt.JwtService;
import com.delivery.mydelivery.utility.password.PasswordValidationUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AccountService {
    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private AccountStatusService accountStatusService;
    @Autowired
    private RoleService roleService;
    @Autowired
    private EmployeeService employeeService;
    @Autowired
    private OrganizationService organizationService;
    @Autowired
    private JwtService jwtService;

    //TODO make custom exception, now returned 200 with empty request if some error occurred
    public String validateAuthCredential(AuthCredential authCredential) {
        Account account = accountRepository.findByEmail(authCredential.getEmail());
        if (account == null) throw new RuntimeException();
        if (account.getAccountStatus().getName() == AccountStatusEnum.DISABLED) throw new AccessDeniedException("Account is disabled");
        if(!PasswordValidationUtil.validatePassword(authCredential.getPassword(), account.getPassword()))
            throw new RuntimeException();
        return jwtService.generateToken(String.valueOf(account.getId()));
    }
    public AccountData getAccountData(Long id) {
        return accountRepository.findAccountDataById(id);
    }
    public Boolean updatePassword(Long id, String updatedPass) {
        Account account = accountRepository.findById(id).orElseThrow(NullPointerException::new);
        account.setPassword(PasswordValidationUtil.hashPassword(updatedPass));
        accountRepository.save(account);
        return true;
    }
    public Organization getOrganizationByAccountId(Long id) {
        Account account = accountRepository.findById(id).orElseThrow(NullPointerException::new);
        return account.getEmployee().getOrganization();
    }
    //TODO Unstable test
    public Account createAccount(Account account) {
        account.setPassword(PasswordValidationUtil.hashPassword(account.getPassword()));
        return accountRepository.save(account);
    }
    public Account getAccountById(Long id) {
        return accountRepository.findById(id).orElseThrow(NullPointerException::new);
    }
    public List<AccountData> getAccountsDataByOrganizationId(Long organizationId) {
        return accountRepository.findAccountDataByOrganizationId(organizationId);
    }
    public AccountStatusEnum updateAccountStatus(Long id, AccountStatusEnum status) {
        Account account = accountRepository.findById(id).orElseThrow(NullPointerException::new);
        AccountStatus accountStatus = accountStatusService.getAccountStatusByName(status);
        account.setAccountStatus(accountStatus);
        return accountRepository.save(account).getAccountStatus().getName();
    }
    public Long createAccount(AccountData accountData) {
        Organization organization = organizationService.getOrganizationById(accountData.getOrganizationId());
        Employee employee = new Employee();
        employee.setName(accountData.getEmployeeName());
        employee.setSecondName(accountData.getEmployeeSecondName());
        employee.setPatronymic(accountData.getEmployeePatronymic());
        employee.setPhone(accountData.getPhone());
        employee.setOrganization(organization);
        employeeService.createEmployee(employee);

        AccountStatus accountStatus = accountStatusService.getAccountStatusByName(AccountStatusEnum.ENABLED);
        Role role = roleService.getRoleByName(accountData.getRole());
        Account account = new Account();
        account.setAccountStatus(accountStatus);
        account.setEmail(accountData.getEmail());
        account.setEmployee(employee);
        account.setPassword(PasswordValidationUtil.hashPassword("123456"));
        account.setRole(role);
        return accountRepository.save(account).getId();
    }
    public boolean checkRoleForAccountId(RoleEnum roleName, Long accountId) {
        Role role = roleService.getRoleByName(roleName);
        if (role == null)
            throw new IllegalArgumentException("Role not found: " + roleName);
        Account account = getAccountById(accountId);
        return account != null && account.getRole().getId().equals(role.getId());
    }
}
