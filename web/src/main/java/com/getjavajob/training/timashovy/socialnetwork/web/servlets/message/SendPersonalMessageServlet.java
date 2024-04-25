package com.getjavajob.training.timashovy.socialnetwork.web.servlets.message;

import com.getjavajob.training.timashovy.socialnetwork.common.account.Account;
import com.getjavajob.training.timashovy.socialnetwork.common.message.Message;
import com.getjavajob.training.timashovy.socialnetwork.service.serviceimpl.message.MessageServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static java.lang.Long.valueOf;

public class SendPersonalMessageServlet extends HttpServlet {

    private final MessageServiceImpl messageService = MessageServiceImpl.getInstance();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        messageService.createPersonalMessage(new Message.Builder()
                .accountAuthorId(((Account) req.getSession(false).getAttribute("account")).getId())
                .destinationId(valueOf(req.getParameter("id")))
                .text(req.getParameter("text"))
                .photo(req.getPart("photo") == null ? null : req.getPart("photo").getInputStream())
                .build());
        resp.sendRedirect("/account/messages/dialog?id=" + req.getParameter("id"));
    }

}
