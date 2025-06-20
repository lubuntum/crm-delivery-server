package com.delivery.mydelivery.aspect.accountmanage;

import com.delivery.mydelivery.annotation.accountmanage.CheckStatus;
import com.delivery.mydelivery.database.entities.accountmanage.Account;
import com.delivery.mydelivery.database.entities.accountmanage.accountstatus.ActiveStatus;
import com.delivery.mydelivery.database.entities.accountmanage.accountstatus.ActiveStatusEnum;
import com.delivery.mydelivery.database.entities.organization.Organization;
import com.delivery.mydelivery.database.services.accountmanage.AccountService;
import com.delivery.mydelivery.database.services.organization.OrganizationService;
import com.delivery.mydelivery.services.jwt.JwtService;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class CheckStatusAspect {
    @Autowired
    private AccountService accountService;
    @Autowired
    private JwtService jwtService;

    @Before("@annotation(checkStatus)")
    public void checkActiveStatus(JoinPoint joinPoint, CheckStatus checkStatus) throws Throwable {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String jwtToken = (String) authentication.getCredentials();
            Long accountId = Long.valueOf(jwtService.extractSubject(jwtToken));
            Organization organization = accountService.getOrganizationByAccountId(accountId);
            if (organization.getOrganizationActiveStatus().getName() != ActiveStatusEnum.ENABLED) {
                throw new AccessDeniedException(checkStatus.message());
            }
        } catch (Exception e) {
            throw new AccessDeniedException(e.getMessage());
        }
    }
}
