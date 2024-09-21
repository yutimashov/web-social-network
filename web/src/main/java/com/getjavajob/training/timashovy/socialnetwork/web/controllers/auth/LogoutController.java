package com.getjavajob.training.timashovy.socialnetwork.web.controllers.auth;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@RequestMapping("/logout")
@Controller
public class LogoutController {

    private static final int EXPIRATION_COOKIE_LIFE_TIME = 0;

    @GetMapping
    public String logoutPage(HttpSession session, HttpServletRequest req, HttpServletResponse resp) {
        invalidateSession(session);
        clearCookies(req, resp);
        for (Cookie cookie : req.getCookies()) {
            cookie.setMaxAge(EXPIRATION_COOKIE_LIFE_TIME);
            resp.addCookie(cookie);
        }
        return "redirect:/login";
    }

    private void invalidateSession(HttpSession session) {
        session.invalidate();
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
