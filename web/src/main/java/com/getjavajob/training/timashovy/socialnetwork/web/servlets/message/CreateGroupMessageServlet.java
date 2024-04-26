package com.getjavajob.training.timashovy.socialnetwork.web.servlets.message;

import com.getjavajob.training.timashovy.socialnetwork.common.account.Account;
import com.getjavajob.training.timashovy.socialnetwork.common.message.Message;
import com.getjavajob.training.timashovy.socialnetwork.service.interfaces.MessageService;
import com.getjavajob.training.timashovy.socialnetwork.service.serviceimpl.message.MessageServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static java.lang.Long.valueOf;

public class CreateGroupMessageServlet extends HttpServlet {

    private final MessageService messageService = MessageServiceImpl.getInstance();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        messageService.createGroupMessage(new Message.Builder()
                .accountAuthorId(((Account) req.getSession(false).getAttribute("account")).getId())
                .destinationId(valueOf(req.getParameter("groupId")))
                .text(req.getParameter("text"))
                .photo(req.getPart("photo").getSize() > 0 ? req.getPart("photo").getInputStream() : null)
                .build());
        resp.sendRedirect("/group?id=" + req.getParameter("groupId"));
    }

}
