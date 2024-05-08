package com.getjavajob.training.timashovy.socialnetwork.web.servlets.account;

import com.getjavajob.training.timashovy.socialnetwork.common.account.PhoneType;
import com.getjavajob.training.timashovy.socialnetwork.service.interfaces.AccountService;
import com.getjavajob.training.timashovy.socialnetwork.service.serviceimpl.account.AccountServiceImpl;
import com.getjavajob.training.timashovy.socialnetwork.service.serviceimpl.account.PhoneServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDate;

import static com.getjavajob.training.timashovy.socialnetwork.common.account.PhoneType.PERSONAL;
import static com.getjavajob.training.timashovy.socialnetwork.common.account.PhoneType.WORKING;
import static com.getjavajob.training.timashovy.socialnetwork.web.util.JspDestinationPath.getJspPagePath;
import static java.lang.Long.valueOf;
import static java.util.Objects.isNull;

public class EditAccountServlet extends HttpServlet {

    private static final String FIRST_NAME_PARAMETER_NAME = "name";
    private static final String LAST_NAME_PARAMETER_NAME = "lastName";
    private static final String MIDDLE_NAME_PARAMETER_NAME = "middleName";
    private static final String BIRTHDATE_PARAMETER_NAME = "birthDate";
    private static final String SKYPE_PARAMETER_NAME = "skype";
    private static final String ICQ_PARAMETER_NAME = "icq";
    private static final String EMAIL_PARAMETER_NAME = "email";
    private final AccountService accountService = AccountServiceImpl.getInstance();
    private final PhoneServiceImpl phoneService = PhoneServiceImpl.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Long accountId = valueOf(req.getParameter("id"));
        if (accountService.getById(accountId).isPresent()) {
            req.setAttribute("account", accountService.getById(accountId).get());
            req.setAttribute("avatarInputStream", accountService.getById(accountId).get().getAvatar());
            req.setAttribute("personalPhones", phoneService.getPersonalPhoneNumbers(accountId));
            req.setAttribute("workingPhones", phoneService.getWorkPhoneNumbers(accountId));
            req.getRequestDispatcher(getJspPagePath("/account/edit")).forward(req, resp);
        } else {
            req.getRequestDispatcher("/WEB-INF/jsp/error/404.jsp").forward(req, resp);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        updateAccountData(req);
        resp.sendRedirect("/account?id=" + req.getParameter("id"));
    }

    private void updateAccountData(HttpServletRequest req) throws ServletException, IOException {
        Long accountId = valueOf(req.getParameter("id"));
        InputStream updatedAvatar = req.getPart("avatar").getInputStream();
        if (!isNull(updatedAvatar)) {
            accountService.updateAvatar(accountId, updatedAvatar);
        }
        String updatedFirstName = getParameter(req, FIRST_NAME_PARAMETER_NAME);
        if (checkParameterHasValue(updatedFirstName)) {
            accountService.updateFirstName(accountId, updatedFirstName);
        }
        String updatedLastName = getParameter(req, LAST_NAME_PARAMETER_NAME);
        if (checkParameterHasValue(updatedLastName)) {
            accountService.updateLastName(accountId, updatedLastName);
        }
        String updatedMiddleName = getParameter(req, MIDDLE_NAME_PARAMETER_NAME);
        if (checkParameterHasValue(updatedMiddleName)) {
            accountService.updateMiddleName(accountId, updatedMiddleName);
        }
        String updatedBirthDate = getParameter(req, BIRTHDATE_PARAMETER_NAME);
        if (checkParameterHasValue(updatedBirthDate)) {
            accountService.updateBirthDate(accountId, LocalDate.parse(updatedBirthDate));
        }
        String updatedSkype = getParameter(req, SKYPE_PARAMETER_NAME);
        if (checkParameterHasValue(updatedSkype)) {
            accountService.updateSkype(accountId, updatedSkype);
        }
        processPhoneNumber(req, PERSONAL);
        processPhoneNumber(req, WORKING);
        String updatedICQ = getParameter(req, ICQ_PARAMETER_NAME);
        if (checkParameterHasValue(updatedICQ)) {
            accountService.updateIcq(accountId, updatedICQ);
        }
        String updatedEmail = getParameter(req, EMAIL_PARAMETER_NAME);
        if (checkParameterHasValue(updatedEmail)) {
            accountService.updateEmail(accountId, updatedEmail);
        }
    }

    private String getParameter(HttpServletRequest req, String parameterName) {
        return req.getParameter(parameterName);
    }

    private boolean checkParameterHasValue(String parameterValue) {
        return !isNull(parameterValue) && !parameterValue.isEmpty();
    }

    private void processPhoneNumber(HttpServletRequest req, PhoneType phoneType) {
        String[] phoneIds;
        String[] phoneValues;
        if (phoneType == PERSONAL) {
            phoneIds = req.getParameterValues("personalPhoneId");
            phoneValues = req.getParameterValues("personalPhoneValue");
        } else {
            phoneIds = req.getParameterValues("workingPhoneId");
            phoneValues = req.getParameterValues("workingPhoneValue");
        }
        if (!isNull(phoneIds) && !isNull(phoneValues)) {
            for (int i = 0; i < phoneIds.length; i++) {
                phoneService.updateById(valueOf(phoneIds[i]), phoneValues[i]);
            }
        }
    }

}
