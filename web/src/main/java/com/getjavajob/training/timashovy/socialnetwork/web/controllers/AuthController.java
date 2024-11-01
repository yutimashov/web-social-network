package com.getjavajob.training.timashovy.socialnetwork.web.controllers;

import com.getjavajob.training.timashovy.socialnetwork.domain.account.Account;
import com.getjavajob.training.timashovy.socialnetwork.domain.util.AccountRegistrationData;
import com.getjavajob.training.timashovy.socialnetwork.service.interfaces.AccountService;
import com.getjavajob.training.timashovy.socialnetwork.service.interfaces.LoginService;
import com.getjavajob.training.timashovy.socialnetwork.service.interfaces.PasswordService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Optional;

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

    private final static Logger logger = LoggerFactory.getLogger(AccountController.class);

    public AuthController(LoginService loginService, PasswordService passwordService, AccountService accountService) {
        this.loginService = loginService;
        this.passwordService = passwordService;
        this.accountService = accountService;
    }

    @GetMapping("/login")
    public String loginPage() {
        return "auth/login";
    }

    @PostMapping("/login")
    public String login(@RequestParam String email, @RequestParam String password,
                        @RequestParam Optional<String> rememberMe, Model model,
                        HttpServletResponse resp) {
        logger.info("going to get loggedIn account with password = {} and email = {}", password, email);
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

    @PostMapping("/register")
    public String processAccountRegistration(@ModelAttribute Account account,
                                             @RequestParam("password") String password,
                                             @RequestParam("personalPhones") String personalPhones,
                                             @RequestParam("workingPhones") String workingPhones) {
        accountService.create(new AccountRegistrationData.Builder()
                .account(account)
                .password(password)
                .personalPhoneNumber(personalPhones)
                .workPhoneNumber(workingPhones)
                .build()
        );
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
