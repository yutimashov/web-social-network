package com.getjavajob.training.timashovy.socialnetwork.web.servlets.auth;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@Controller
public class LogoutController {

    @GetMapping("/logout")
    public String logoutPage(HttpSession session, HttpServletRequest req, HttpServletResponse resp) {
        session.invalidate();
        for (Cookie cookie : req.getCookies()) {
            int EXPIRATION_COOKIE_TIME = 0;
            cookie.setMaxAge(EXPIRATION_COOKIE_TIME);
            resp.addCookie(cookie);
        }
        return "redirect:/login";
    }

}
