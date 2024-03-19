package com.getjavajob.training.timashovy.socialnetwork.web.servlets;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class LogoutServlet extends HttpServlet {

    private static final int EXPIRATION_COOKIE_TIME = 0;

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        req.getSession().invalidate();
        invalidateCookies(req, resp);
        resp.sendRedirect("/login");
    }

    private void invalidateCookies(HttpServletRequest req, HttpServletResponse resp) {
        Cookie[] cookies = req.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                cookie.setMaxAge(EXPIRATION_COOKIE_TIME);
                resp.addCookie(cookie);
            }
        }
    }

}
