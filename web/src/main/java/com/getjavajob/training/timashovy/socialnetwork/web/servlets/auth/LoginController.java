package com.getjavajob.training.timashovy.socialnetwork.web.servlets.auth;

import com.getjavajob.training.timashovy.socialnetwork.common.account.Account;
import com.getjavajob.training.timashovy.socialnetwork.service.interfaces.LoginService;
import com.getjavajob.training.timashovy.socialnetwork.service.interfaces.PasswordService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Optional;

import static com.getjavajob.training.timashovy.socialnetwork.web.util.StatusTypes.AUTH_DATA_ERROR;
import static java.util.concurrent.TimeUnit.HOURS;

@Controller
public class LoginController {

    private final LoginService loginService;
    private final PasswordService passwordService;
    private final int rememberMeCookieLifetime = (int) HOURS.toSeconds(1);

    public LoginController(LoginService loginService, PasswordService passwordService) {
        this.loginService = loginService;
        this.passwordService = passwordService;
    }

    @GetMapping("/login")
    public String loginPage() {
        return "auth/login";
    }

    @PostMapping("/login")
    public String login(@RequestParam String email, @RequestParam String password,
                        @RequestParam Optional<String> rememberMe, HttpSession session,
                        HttpServletResponse resp) {
        Optional<Account> loggedInAccount = loginService.getLoggedInAccount(email, password);
        if (loggedInAccount.isPresent()) {
            Account account = loggedInAccount.get();
            session.setAttribute("account", account);
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
            prepareCookie(resp, "password", passwordService.get(account.getId()).get().getPassword());
        }
    }

    private void prepareCookie(HttpServletResponse resp, String cookieName, String cookieValue) {
        Cookie cookie = new Cookie(cookieName, cookieValue);
        cookie.setMaxAge(rememberMeCookieLifetime);
        resp.addCookie(cookie);
    }

}
