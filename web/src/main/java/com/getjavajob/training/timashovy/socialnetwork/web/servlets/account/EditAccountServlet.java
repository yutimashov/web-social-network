package com.getjavajob.training.timashovy.socialnetwork.web.servlets.account;

import com.getjavajob.training.timashovy.socialnetwork.service.interfaces.ImageService;
import com.getjavajob.training.timashovy.socialnetwork.service.serviceimpl.AccountServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDate;

import static com.getjavajob.training.timashovy.socialnetwork.service.serviceimpl.AccountServiceImpl.getAccountServiceInstance;
import static com.getjavajob.training.timashovy.socialnetwork.service.serviceimpl.AccountAvatarServiceImpl.getAccountAvatarServiceInstance;
import static com.getjavajob.training.timashovy.socialnetwork.web.util.JspDestinationPath.getJspPagePath;
import static java.lang.Long.valueOf;

public class EditAccountServlet extends HttpServlet {

    private final AccountServiceImpl accountService = getAccountServiceInstance();
    private final ImageService avatarService = getAccountAvatarServiceInstance();
    private static final String FIRST_NAME_PARAMETER_NAME = "name";
    private static final String LAST_NAME_PARAMETER_NAME = "lastName";
    private static final String MIDDLE_NAME_PARAMETER_NAME = "middleName";
    private static final String BIRTHDATE_PARAMETER_NAME = "birthDate";
    private static final String SKYPE_PARAMETER_NAME = "skype";
    private static final String ICQ_PARAMETER_NAME = "icq";
    private static final String EMAIL_PARAMETER_NAME = "email";

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Long accountId = valueOf(req.getParameter("id"));
        if (accountService.getAccountById(accountId).isPresent()) {
            req.setAttribute("account", accountService.getAccountById(accountId).get());
            req.setAttribute("avatarInputStream", avatarService.get(accountId));
            req.getRequestDispatcher(getJspPagePath("account-edit")).forward(req, resp);
        } else {
            req.getRequestDispatcher("/WEB-INF/jsp/error/404.jsp").forward(req, resp);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        updateAccountData(req);
        resp.sendRedirect("/account?id=" + req.getParameter("id"));
    }

    private void updateAccountData(HttpServletRequest req) {
        Long accountId = valueOf(req.getParameter("id"));
        String updatedFirstName = getParameter(req, FIRST_NAME_PARAMETER_NAME);
        if (checkParameterHasValue(updatedFirstName)) {
            accountService.updateFirstName(accountId, updatedFirstName);
        }
        String updatedLastName = getParameter(req, LAST_NAME_PARAMETER_NAME);
        if (checkParameterHasValue(updatedLastName)) {
            accountService.updateAccountLastName(accountId, updatedLastName);
        }
        String updatedMiddleName = getParameter(req, MIDDLE_NAME_PARAMETER_NAME);
        if (checkParameterHasValue(updatedMiddleName)) {
            accountService.updateAccountMiddleName(accountId, updatedMiddleName);
        }
        String updatedBirthDate = getParameter(req, BIRTHDATE_PARAMETER_NAME);
        if (checkParameterHasValue(updatedBirthDate)) {
            accountService.updateAccountBirthDate(accountId, LocalDate.parse(updatedBirthDate));
        }
        String updatedSkype = getParameter(req, SKYPE_PARAMETER_NAME);
        if (checkParameterHasValue(updatedSkype)) {
            accountService.updateAccountSkype(accountId, updatedSkype);
        }
        String updatedICQ = getParameter(req, ICQ_PARAMETER_NAME);
        if (checkParameterHasValue(updatedICQ)) {
            accountService.updateAccountIcq(accountId, updatedICQ);
        }
        String updatedEmail = getParameter(req, EMAIL_PARAMETER_NAME);
        if (checkParameterHasValue(updatedEmail)) {
            accountService.updateAccountEmail(accountId, updatedEmail);
        }
    }

    private String getParameter(HttpServletRequest req, String parameterName) {
        return req.getParameter(parameterName);
    }

    private boolean checkParameterHasValue(String parameterValue) {
        return !"".equals(parameterValue);
    }

}
