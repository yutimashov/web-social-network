package com.getjavajob.training.timashovy.socialnetwork.service.serviceimpl.account;

//@Service
//public class LoginServiceImpl implements LoginService {
//
//    private final AccountService accountService;
//    private final PasswordService passwordService;
//
//    private final static Logger logger = LoggerFactory.getLogger(LoginServiceImpl.class);
//
//    public LoginServiceImpl(AccountService accountService, PasswordService passwordService) {
//        this.accountService = accountService;
//        this.passwordService = passwordService;
//    }
//
//    @Override
//    public Optional<Account> getLoggedInAccount(String email, String password) {
//        if (isNull(email) || isNull(password)) {
//            return empty();
//        }
//        Optional<Password> accountPassword = passwordService.findPasswordByEmail(email);
//        if (!accountPassword.isPresent()) {
//            return empty();
//        }
//        Password dbPassword = accountPassword.get();
//        return dbPassword.getPasswordValue().equals(hashCredentialData(password, dbPassword.getSalt()))
//                ? accountService.getById(dbPassword.getId())
//                : empty();
//    }
//
//}
