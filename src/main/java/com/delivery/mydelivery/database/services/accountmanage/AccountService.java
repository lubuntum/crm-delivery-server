package com.delivery.mydelivery.database.services.accountmanage;

import com.delivery.mydelivery.database.entities.accountmanage.Account;
import com.delivery.mydelivery.database.entities.accountmanage.accountstatus.AccountStatus;
import com.delivery.mydelivery.database.entities.accountmanage.accountstatus.AccountStatusEnum;
import com.delivery.mydelivery.database.entities.organization.Organization;
import com.delivery.mydelivery.database.repositories.accountmanage.AccountRepository;
import com.delivery.mydelivery.dto.auth.AccountData;
import com.delivery.mydelivery.dto.auth.AuthCredential;
import com.delivery.mydelivery.services.jwt.JwtService;
import com.delivery.mydelivery.utility.password.PasswordValidationUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AccountService {
    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private AccountStatusService accountStatusService;
    @Autowired
    private JwtService jwtService;

    //TODO make custom exception, now returned 200 with empty request if some error occurred
    public String validateAuthCredential(AuthCredential authCredential) {
        Account account = accountRepository.findByEmail(authCredential.getEmail());
        if (account == null) throw new RuntimeException();
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
}
