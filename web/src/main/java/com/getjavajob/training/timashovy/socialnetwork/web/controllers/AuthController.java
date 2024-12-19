package com.getjavajob.training.timashovy.socialnetwork.web.controllers;

import com.getjavajob.training.timashovy.socialnetwork.domain.account.Account;
import com.getjavajob.training.timashovy.socialnetwork.service.interfaces.AccountService;
import com.getjavajob.training.timashovy.socialnetwork.service.interfaces.PasswordService;
import com.getjavajob.training.timashovy.socialnetwork.web.dto.AccountDto;
import com.getjavajob.training.timashovy.socialnetwork.web.mappers.AccountMapper;
import org.slf4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import static com.getjavajob.training.timashovy.socialnetwork.web.util.UrlStatusParameter.REG_SUCCESS;
import static org.slf4j.LoggerFactory.getLogger;

@SessionAttributes("account")
@Controller
public class AuthController {

    private static final int EXPIRATION_COOKIE_LIFE_TIME = 0;
    private static final int REMEMBER_ME_COOKIE_LIFE_TIME = 3600;
//    private final LoginService loginService;
    private final PasswordService passwordService;
    private final AccountService accountService;
    private static final Logger logger = getLogger(AuthController.class);

    public AuthController(PasswordService passwordService, AccountService accountService) {
        this.passwordService = passwordService;
        this.accountService = accountService;
    }

    @GetMapping("/login")
    public String loginPage() {
        return "auth/login";
    }

//    @PostMapping("/login")
//    public String login(@RequestParam String email,
//                        @RequestParam String password,
//                        @RequestParam(required = false) String rememberMe,
//                        Model model,
//                        HttpServletResponse resp) {
//        Optional<Account> loggedInAccount = loginService.getLoggedInAccount(email, password);
//        if (loggedInAccount.isPresent()) {
//            Account account = loggedInAccount.get();
//            model.addAttribute("account", account);
//            if (!isNull(rememberMe)) {
//                createRememberMeCookie(account, resp);
//            }
//            logger.info("Account={} logged in successfully", account.getId());
//            return "redirect:/account?id=" + account.getId();
//        } else {
//            return "redirect:/login" + AUTH_DATA_ERROR.getValue();
//        }
//    }
//
//    private void createRememberMeCookie(Account account, HttpServletResponse resp) {
//        resp.addCookie(createCookie("login", account.getEmail()));
//        if (passwordService.get(account.getId()).isPresent()) {
//            resp.addCookie(createCookie("password", passwordService.get(account.getId()).get()
//                    .getPasswordValue()));
//        }
//    }
//
//    private Cookie createCookie(String cookieName, String cookieValue) {
//        Cookie cookie = new Cookie(cookieName, cookieValue);
//        cookie.setMaxAge(REMEMBER_ME_COOKIE_LIFE_TIME);
//        return cookie;
//    }

    @GetMapping("/register")
    public String getRegisterPage() {
        return "auth/register";
    }

    @PostMapping("/register")
    public String processAccountRegistration(@ModelAttribute AccountDto accountDto,
                                             @RequestParam("password") String password,
                                             @RequestParam("personalPhones") String personalPhones,
                                             @RequestParam("workingPhones") String workingPhones) {
        Account account = accountService.create(new AccountMapper().toAccount(accountDto), password, personalPhones,
                workingPhones);
        logger.info("New account={} has been registered", account.getId());
        return "redirect:/login" + REG_SUCCESS.getValue();
    }

    @GetMapping("/logout")
    public String logoutPage(HttpSession session, HttpServletRequest req, HttpServletResponse resp) {
        session.invalidate();
        clearAllCookies(req, resp);
        return "redirect:/login";
    }

    private void clearAllCookies(HttpServletRequest req, HttpServletResponse resp) {
        Cookie[] cookies = req.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                resp.addCookie(clearCookie(cookie));
            }
        }
    }

    private Cookie clearCookie(Cookie cookie) {
        cookie.setMaxAge(EXPIRATION_COOKIE_LIFE_TIME);
        cookie.setValue(null);
        cookie.setPath("/");
        return cookie;
    }

}
