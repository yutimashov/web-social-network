package com.getjavajob.training.timashovy.socialnetwork.web.controllers;

import com.getjavajob.training.timashovy.socialnetwork.domain.account.Account;
import com.getjavajob.training.timashovy.socialnetwork.domain.password.Password;
import com.getjavajob.training.timashovy.socialnetwork.service.interfaces.AccountService;
import com.getjavajob.training.timashovy.socialnetwork.service.interfaces.LoginService;
import com.getjavajob.training.timashovy.socialnetwork.service.interfaces.PasswordService;
import com.getjavajob.training.timashovy.socialnetwork.service.interfaces.PhoneService;
import com.getjavajob.training.timashovy.socialnetwork.web.dto.AccountDto;
import com.getjavajob.training.timashovy.socialnetwork.web.mappers.AccountMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Optional;

import static com.getjavajob.training.timashovy.socialnetwork.service.util.PasswordUtil.generateSalt;
import static com.getjavajob.training.timashovy.socialnetwork.service.util.PasswordUtil.hashCredentialData;
import static com.getjavajob.training.timashovy.socialnetwork.web.util.StatusTypes.AUTH_DATA_ERROR;
import static com.getjavajob.training.timashovy.socialnetwork.web.util.StatusTypes.REG_SUCCESS;
import static java.util.concurrent.TimeUnit.HOURS;

@SessionAttributes("account")
@Controller
public class AuthController {

    private static final int EXPIRATION_COOKIE_LIFE_TIME = 0;
    private final static int REMEMBER_ME_COOKIE_LIFE_TIME = (int) HOURS.toSeconds(1);
    private final LoginService loginService;
    private final PasswordService passwordService;
    private final AccountService accountService;
    private final PhoneService phoneService;

    private final static Logger logger = LoggerFactory.getLogger(AccountController.class);

    public AuthController(LoginService loginService, PasswordService passwordService, AccountService accountService,
                          PhoneService phoneService) {
        this.loginService = loginService;
        this.passwordService = passwordService;
        this.accountService = accountService;
        this.phoneService = phoneService;
    }

    @GetMapping("/login")
    public String loginPage() {
        return "auth/login";
    }

    @PostMapping("/login")
    public String login(@RequestParam String email, @RequestParam String password,
                        @RequestParam Optional<String> rememberMe, Model model,
                        HttpServletResponse resp) {
        Optional<Account> loggedInAccount = loginService.getLoggedInAccount(email, password);
        if (loggedInAccount.isPresent()) {
            Account account = loggedInAccount.get();
            model.addAttribute("account", account);
            if (rememberMe.isPresent()) {
                createRememberMeCookies(account, resp);
            }
            return "redirect:/account?id=" + account.getId();
        } else {
            return "redirect:/login" + AUTH_DATA_ERROR;
        }
    }

    private void createRememberMeCookies(Account account, HttpServletResponse resp) {
        prepareCookie(resp, "login", account.getEmail());
        if (passwordService.get(account.getId()).isPresent()) {
            prepareCookie(resp, "password", passwordService.get(account.getId()).get().getPasswordValue());
        }
    }

    private void prepareCookie(HttpServletResponse resp, String cookieName, String cookieValue) {
        Cookie cookie = new Cookie(cookieName, cookieValue);
        cookie.setMaxAge(REMEMBER_ME_COOKIE_LIFE_TIME);
        resp.addCookie(cookie);
    }

    @GetMapping("/register")
    public String getRegisterPage() {
        return "auth/register";
    }

    @Transactional
    @PostMapping("/register")
    public String processAccountRegistration(@ModelAttribute AccountDto accountDto,
                                             @RequestParam("password") String password,
                                             @RequestParam("personalPhones") String personalPhones,
                                             @RequestParam("workingPhones") String workingPhones) throws IOException {
        Account account = new AccountMapper().toAccount(accountDto);
        Long accountId = accountService.create(account);
        account.setId(accountId);
        // Получить управляемый объект Account, если он не в управляемом состоянии
        account = accountService.getById(accountId).get();
        String salt = generateSalt();
        Password accountPassword = new Password(account, hashCredentialData(password, salt), salt);
        account.setPassword(accountPassword);
//        passwordService.create(account, password);
//        phoneService.createPersonalPhones(account, personalPhones);
//        phoneService.createWorkingPhones(account, workingPhones);
        return "redirect:/login" + REG_SUCCESS;
    }

    @GetMapping("/logout")
    public String logoutPage(HttpSession session, HttpServletRequest req, HttpServletResponse resp) {
        session.invalidate();
        clearCookies(req, resp);
        return "redirect:/login";
    }

    private void clearCookies(HttpServletRequest req, HttpServletResponse resp) {
        Cookie[] cookies = req.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                cookie.setMaxAge(EXPIRATION_COOKIE_LIFE_TIME);
                cookie.setValue(null);
                cookie.setPath("/");
                resp.addCookie(cookie);
            }
        }
    }

}
