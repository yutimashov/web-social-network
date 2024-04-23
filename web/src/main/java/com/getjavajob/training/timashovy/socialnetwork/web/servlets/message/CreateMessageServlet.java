package com.getjavajob.training.timashovy.socialnetwork.web.servlets.message;

import com.getjavajob.training.timashovy.socialnetwork.common.account.Account;
import com.getjavajob.training.timashovy.socialnetwork.common.message.Message;
import com.getjavajob.training.timashovy.socialnetwork.common.message.MessageImage;
import com.getjavajob.training.timashovy.socialnetwork.service.interfaces.MessageImageService;
import com.getjavajob.training.timashovy.socialnetwork.service.interfaces.MessageService;
import com.getjavajob.training.timashovy.socialnetwork.service.serviceimpl.message.MessageImageServiceImpl;
import com.getjavajob.training.timashovy.socialnetwork.service.serviceimpl.message.MessageServiceImpl;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.IOException;

import static com.getjavajob.training.timashovy.socialnetwork.common.message.MessageType.GROUP;

public class CreateMessageServlet extends HttpServlet {

    private final MessageService messageService = MessageServiceImpl.getInstance();
    private final MessageImageService messageImageService = MessageImageServiceImpl.getInstance();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Long accountAuthorId = ((Account) req.getSession(false).getAttribute("account")).getId();
        Long messageId = messageService.create(new Message(accountAuthorId, req.getParameter("messageText"), GROUP));
        messageImageService.create(new MessageImage(req.getPart("messageImage").getInputStream(), messageId));
        resp.sendRedirect("/group/all");
    }

}
