package com.getjavajob.training.timashovy.socialnetwork.web.servlets.message.personal;

import com.getjavajob.training.timashovy.socialnetwork.common.account.Account;
import com.getjavajob.training.timashovy.socialnetwork.common.message.Message;
import com.getjavajob.training.timashovy.socialnetwork.service.serviceimpl.message.MessageServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.getjavajob.training.timashovy.socialnetwork.service.util.singletonsregistry.ServiceSingletonRegistry.getServiceSingletonRegistry;
import static com.getjavajob.training.timashovy.socialnetwork.service.util.singletonsregistry.ServiceSingletonsNames.MESSAGE_SERVICE_SINGLETON;
import static java.lang.Long.valueOf;

public class SendMessageServlet extends HttpServlet {

    private final MessageServiceImpl messageService = getServiceSingletonRegistry()
            .getSingleton(MESSAGE_SERVICE_SINGLETON);

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        messageService.createPersonalMessage(new Message.Builder()
                .accountAuthorId(((Account) req.getSession(false).getAttribute("account")).getId())
                .destinationId(valueOf(req.getParameter("accountReceiverId")))
                .text(req.getParameter("text"))
                .photo(req.getPart("photo").getSize() > 0 ? req.getPart("photo").getInputStream() : null)
                .build());
        resp.sendRedirect("/account/messages/dialog?id=" + req.getParameter("accountReceiverId"));
    }

}
