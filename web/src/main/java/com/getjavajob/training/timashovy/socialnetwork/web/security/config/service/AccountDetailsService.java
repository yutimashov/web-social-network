package com.getjavajob.training.timashovy.socialnetwork.web.security.config.service;

import com.getjavajob.training.timashovy.socialnetwork.domain.account.Account;
import com.getjavajob.training.timashovy.socialnetwork.domain.password.Password;
import com.getjavajob.training.timashovy.socialnetwork.service.interfaces.AccountService;
import com.getjavajob.training.timashovy.socialnetwork.service.interfaces.PasswordService;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class AccountDetailsService implements UserDetailsService {

    private final AccountService accountService;
    private final PasswordService passwordService;

    public AccountDetailsService(AccountService accountService, PasswordService passwordService) {
        this.accountService = accountService;
        this.passwordService = passwordService;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Account account = accountService.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("cannot find account with provided email"));
        Password password = passwordService.get(account.getId())
                .orElseThrow(() -> new UsernameNotFoundException("cannot find password for account with provided email"));
        String passwordValue = password.getPasswordValue();
        return User.builder()
                .username(account.getEmail())
                .password(passwordValue)
                .roles(account.getRole().name())
                .build();
    }

}
