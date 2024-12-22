package com.getjavajob.training.timashovy.socialnetwork.service.serviceimpl.security;

import com.getjavajob.training.timashovy.socialnetwork.domain.account.Account;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;

/**
 * Extends User with additional field of Account instance, which represents authenticated account.
 */
public class AccountUserDetails extends User {

    private final Account account;

    public AccountUserDetails(String username, String password, Collection<? extends GrantedAuthority> authorities,
                              Account account) {
        super(username, password, authorities);
        this.account = account;
    }

    public Account getAccount() {
        return account;
    }

}
