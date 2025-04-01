package com.delivery.mydelivery.database.services.accountmanage;

import com.delivery.mydelivery.database.entities.accountmanage.Account;
import com.delivery.mydelivery.database.repositories.accountmanage.AccountRepository;
import com.delivery.mydelivery.dto.auth.AuthCredential;
import com.delivery.mydelivery.services.jwt.JwtService;
import com.delivery.mydelivery.utility.password.PasswordValidationUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AccountService {
    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private JwtService jwtService;

    public String validateAuthCredential(AuthCredential authCredential) {
        Account account = accountRepository.findByEmail(authCredential.getEmail());
        if (account == null) return null;
        if(!PasswordValidationUtil.validatePassword(authCredential.getPassword(), account.getPassword()))
            return null;
        return jwtService.generateToken(String.valueOf(account.getId()));
    }
    //TODO Unstable test
    public Account createAccount(Account account) {
        account.setPassword(PasswordValidationUtil.hashPassword(account.getPassword()));
        return accountRepository.save(account);
    }
}
