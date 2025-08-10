package com.delivery.mydelivery.aspect.accountmanage;

import com.delivery.mydelivery.annotation.accountmanage.HasRole;
import com.delivery.mydelivery.database.entities.accountmanage.role.Role;
import com.delivery.mydelivery.database.services.accountmanage.AccountService;
import com.delivery.mydelivery.database.services.accountmanage.RoleService;
import com.delivery.mydelivery.services.jwt.JwtService;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.lang.reflect.Array;
import java.util.Arrays;

@Aspect
@Component
public class RoleAspect {
    @Autowired
    private AccountService accountService;
    @Autowired
    private JwtService jwtService;

    @Before("@annotation(hasRole)")
    public void checkRole(JoinPoint joinPoint, HasRole hasRole) throws Throwable {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String jwtToken = (String) authentication.getCredentials();
            Long accountId = Long.valueOf(jwtService.extractSubject(jwtToken));

            boolean hasAnyRole = Arrays.stream(hasRole.value()).anyMatch(role -> accountService.checkRoleForAccountId(role, accountId));
            if (!hasAnyRole) throw new AccessDeniedException("Don't meet role requirements");
            //if (!accountService.checkRoleForAccountId(hasRole.value(), accountId)) throw new AccessDeniedException("Don't meet role requirements");
        } catch (Exception e) {
            throw new AccessDeniedException(e.getMessage());
        }
    }
}
