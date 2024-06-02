package com.getjavajob.training.timashovy.socialnetwork.web.servlets.auth;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.getjavajob.training.timashovy.socialnetwork.web.util.ServletPaths.LOGIN_SERVLET_PATH;
import static java.util.Objects.isNull;

public class LogoutServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getSession().invalidate();
        invalidateCookies(req, resp);
        resp.sendRedirect(LOGIN_SERVLET_PATH);
    }

    private void invalidateCookies(HttpServletRequest req, HttpServletResponse resp) {
        Cookie[] cookies = req.getCookies();
        if (!isNull(cookies)) {
            for (Cookie cookie : cookies) {
                int EXPIRATION_COOKIE_TIME = 0;
                cookie.setMaxAge(EXPIRATION_COOKIE_TIME);
                resp.addCookie(cookie);
            }
        }
    }

}
