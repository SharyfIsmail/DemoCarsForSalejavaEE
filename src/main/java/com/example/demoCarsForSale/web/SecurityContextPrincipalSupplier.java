package com.example.demoCarsForSale.web;

import com.example.demoCarsForSale.pojo.User;
import org.springframework.security.core.context.SecurityContextHolder;

public abstract class SecurityContextPrincipalSupplier {
    protected static User getUser() {
        return (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }
}
