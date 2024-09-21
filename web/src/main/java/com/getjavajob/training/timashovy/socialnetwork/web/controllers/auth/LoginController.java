package com.getjavajob.training.timashovy.socialnetwork.web.controllers.auth;

import com.getjavajob.training.timashovy.socialnetwork.common.account.Account;
import com.getjavajob.training.timashovy.socialnetwork.service.interfaces.LoginService;
import com.getjavajob.training.timashovy.socialnetwork.service.interfaces.PasswordService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.util.Optional;

import static com.getjavajob.training.timashovy.socialnetwork.web.util.StatusTypes.AUTH_DATA_ERROR;
import static java.util.concurrent.TimeUnit.HOURS;

@SessionAttributes("account")
@RequestMapping("/login")
@Controller
public class LoginController {

    private final LoginService loginService;
    private final PasswordService passwordService;
    private final static int REMEMBER_ME_COOKIE_LIFE_TIME = (int) HOURS.toSeconds(1);

    public LoginController(LoginService loginService, PasswordService passwordService) {
        this.loginService = loginService;
        this.passwordService = passwordService;
    }

    @GetMapping
    public String loginPage() {
        return "auth/login";
    }

    @PostMapping
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
            prepareCookie(resp, "password", passwordService.get(account.getId()).get().getPassword());
        }
    }

    private void prepareCookie(HttpServletResponse resp, String cookieName, String cookieValue) {
        Cookie cookie = new Cookie(cookieName, cookieValue);
        cookie.setMaxAge(REMEMBER_ME_COOKIE_LIFE_TIME);
        resp.addCookie(cookie);
    }

}
