package com.getjavajob.training.timashovy.socialnetwork.web.servlets.group;

import com.getjavajob.training.timashovy.socialnetwork.service.serviceimpl.group.GroupAvatarServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import static com.getjavajob.training.timashovy.socialnetwork.service.serviceimpl.group.GroupAvatarServiceImpl.getInstance;
import static java.lang.Long.valueOf;

public class GroupAvatarServlet extends HttpServlet {

    private final GroupAvatarServiceImpl avatarService = getInstance();

    private static final int BUFFER_SIZE = 4096;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Long groupId = valueOf(req.getParameter("id"));
        InputStream avatarInputStream = avatarService.get(groupId);
        if (!(avatarInputStream == null)) {
            resp.setContentType("image/jpeg");
            try (OutputStream out = resp.getOutputStream()) {
                byte[] buffer = new byte[BUFFER_SIZE];
                int bytesRead;
                while ((bytesRead = avatarInputStream.read(buffer)) != -1) {
                    out.write(buffer, 0, bytesRead);
                }
            }
        }
    }

}
