package com.getjavajob.training.timashovy.socialnetwork.web.servlets;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import static com.getjavajob.training.timashovy.socialnetwork.service.serviceimpl.AvatarServiceImpl.getAvatarService;
import static java.lang.Long.valueOf;

public class AccountAvatarServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Long accountId = valueOf(req.getParameter("id"));
        InputStream avatarInputStream = getAvatarService().getAvatar(accountId);
        resp.setContentType("image/jpeg");
        try (OutputStream out = resp.getOutputStream()) {
            byte[] buffer = new byte[4096];
            int bytesRead;
            while ((bytesRead = avatarInputStream.read(buffer)) != -1) {
                out.write(buffer, 0, bytesRead);
            }
        }
    }

}
