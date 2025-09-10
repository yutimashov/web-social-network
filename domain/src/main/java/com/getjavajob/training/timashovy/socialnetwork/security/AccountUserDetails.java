package com.getjavajob.training.timashovy.socialnetwork.security;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.io.Serial;
import java.io.Serializable;
import java.util.Collection;

/**
 * Extends User with additional field of Account instance, which represents authenticated account.
 */
public class AccountUserDetails extends User implements Serializable {

    @Serial
    private static final long serialVersionUID = -4811599783542647963L;

    public AccountUserDetails(String username, String password, Collection<? extends GrantedAuthority> authorities) {
        super(username, password, authorities);
    }

}
