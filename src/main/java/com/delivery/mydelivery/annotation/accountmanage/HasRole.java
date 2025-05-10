package com.delivery.mydelivery.annotation.accountmanage;

import com.delivery.mydelivery.database.entities.accountmanage.role.RoleEnum;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface HasRole {
    RoleEnum value();
}
