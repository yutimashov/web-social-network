package com.getjavajob.training.timashovy.socialnetwork.web.security.config.service;

import com.getjavajob.training.timashovy.socialnetwork.dao.interfaces.account.AccountRepository;
import com.getjavajob.training.timashovy.socialnetwork.domain.account.Account;
import com.getjavajob.training.timashovy.socialnetwork.domain.password.Password;
import com.getjavajob.training.timashovy.socialnetwork.service.interfaces.PasswordService;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AccountDetailsService implements UserDetailsService {

    private final AccountRepository accountRepository;
    private final PasswordService passwordService;

    public AccountDetailsService(AccountRepository accountRepository, PasswordService passwordService) {
        this.accountRepository = accountRepository;
        this.passwordService = passwordService;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<Account> maybeAccount = accountRepository.findByEmail(email);
        if (maybeAccount.isPresent()) {
            Account account = maybeAccount.get();
            Optional<Password> maybePassword = passwordService.get(account.getId());
            if (maybePassword.isPresent()) {
                String passwordValue = maybePassword.get().getPasswordValue();
                return User.builder()
                        .username(account.getEmail())
                        .password(passwordValue)
                        .roles(account.getRole().name())
                        .build();
            } else {
                throw new UsernameNotFoundException("cannot get password");
            }
        } else {
            throw new UsernameNotFoundException("user with provided email is not found");
        }
    }

}
