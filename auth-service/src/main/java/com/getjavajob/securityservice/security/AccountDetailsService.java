package com.getjavajob.securityservice.security;

import com.getjavajob.securityservice.service.account.AccountService;
import com.getjavajob.securityservice.service.password.PasswordService;
import com.getjavajob.training.timashovy.socialnetwork.domain.account.Account;
import com.getjavajob.training.timashovy.socialnetwork.dto.password.PasswordDTO;
import com.getjavajob.training.timashovy.socialnetwork.security.AccountUserDetails;
import org.slf4j.Logger;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import static org.slf4j.LoggerFactory.getLogger;
import static org.springframework.security.core.authority.AuthorityUtils.createAuthorityList;

@Service
public class AccountDetailsService implements UserDetailsService {

    private static final Logger logger = getLogger(AccountDetailsService.class);
    private final AccountService accountService;
    private final PasswordService passwordService;

    public AccountDetailsService(PasswordService passwordService, AccountService accountService) {
        this.accountService = accountService;
        this.passwordService = passwordService;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Account account = accountService.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("cannot find account with provided email"));
        PasswordDTO passwordDTO = passwordService.get(account.getId());
        return new AccountUserDetails(account.getEmail(), passwordDTO.getPasswordValue(),
                createAuthorityList(account.getRole().name()));
    }

}
