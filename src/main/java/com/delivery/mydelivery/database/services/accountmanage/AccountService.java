package com.delivery.mydelivery.database.services.accountmanage;

import com.delivery.mydelivery.config.EnvPropertiesConfig;
import com.delivery.mydelivery.database.entities.accountmanage.Account;
import com.delivery.mydelivery.database.entities.accountmanage.Employee;
import com.delivery.mydelivery.database.entities.accountmanage.accountstatus.ActiveStatus;
import com.delivery.mydelivery.database.entities.accountmanage.accountstatus.ActiveStatusEnum;
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
import org.springframework.core.env.Environment;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    Environment env;
    @Autowired
    private JwtService jwtService;
    /*TODO make auth and registration by email not case sensitive*/
    public String validateAuthCredential(AuthCredential authCredential) {
        Account account = accountRepository.findByEmail(authCredential.getEmail());
        if (account == null) throw new RuntimeException();
        if (account.getActiveStatus().getName() != ActiveStatusEnum.ENABLED) throw new AccessDeniedException("Can't get access to auth data");
        if(!PasswordValidationUtil.validatePassword(authCredential.getPassword(), account.getPassword()))
            throw new RuntimeException();
        return jwtService.generateToken(String.valueOf(account.getId()));
    }
    public boolean isEmailValid(String email) {
        Account account = accountRepository.findByEmail(email);
        return account == null;
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
    public Employee getEmployeeByAccountId(Long accountId) {
        Account account = accountRepository.findById(accountId).orElseThrow(NullPointerException::new);
        return account.getEmployee();
    }
    public ActiveStatusEnum updateAccountStatus(Long id, ActiveStatusEnum status) {
        Account account = accountRepository.findById(id).orElseThrow(NullPointerException::new);
        ActiveStatus activeStatus = accountStatusService.getAccountStatusByName(status);
        account.setActiveStatus(activeStatus);
        return accountRepository.save(account).getActiveStatus().getName();
    }
    public Long createAccount(AccountData accountData) {
        if (!isEmailValid(accountData.getEmail())) throw new RuntimeException("Email already in use");
        Organization organization = organizationService.getOrganizationById(accountData.getOrganizationId());
        Employee employee = new Employee();
        employee.setName(accountData.getEmployeeName());
        employee.setSecondName(accountData.getEmployeeSecondName());
        employee.setPatronymic(accountData.getEmployeePatronymic());
        employee.setPhone(accountData.getPhone());
        employee.setOrganization(organization);
        employeeService.createEmployee(employee);

        ActiveStatus activeStatus = accountStatusService.getAccountStatusByName(ActiveStatusEnum.ENABLED);
        Role role = roleService.getRoleByName(accountData.getRole());
        Account account = new Account();
        account.setActiveStatus(activeStatus);
        account.setEmail(accountData.getEmail());
        account.setEmployee(employee);
        account.setPassword(PasswordValidationUtil.hashPassword(env.getProperty(EnvPropertiesConfig.DEFAULT_PASSWORD)));
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
    @Transactional
    public boolean updateAccountData(AccountData accountData) {
        Account account = accountRepository.findById(accountData.getId()).orElseThrow(NullPointerException::new);
        Employee employee = account.getEmployee();
        Organization organization = account.getEmployee().getOrganization();
        boolean updated = false;
        if (accountData.getPhone() != null
                && !accountData.getPhone().equals(employee.getPhone())) {
            employee.setPhone(accountData.getPhone());
            updated = true;
        }
        if (accountData.getOrganizationName() != null
                && !accountData.getOrganizationName().equals(organization.getName())){
            organization.setName(accountData.getOrganizationName());
            updated = true;
        }

        //organizationService.save(organization);
        //employeeService.save(employee);
        //accountRepository.save(account);
        return updated;
    }
}
