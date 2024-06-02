package com.getjavajob.training.timashovy.socialnetwork.web.servlets.account;

import com.getjavajob.training.timashovy.socialnetwork.common.account.Account;
import com.getjavajob.training.timashovy.socialnetwork.common.account.Phone;
import com.getjavajob.training.timashovy.socialnetwork.common.account.PhoneType;
import com.getjavajob.training.timashovy.socialnetwork.common.util.AccountUpdatingData;
import com.getjavajob.training.timashovy.socialnetwork.service.interfaces.AccountService;
import com.getjavajob.training.timashovy.socialnetwork.service.serviceimpl.account.PhoneServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.getjavajob.training.timashovy.socialnetwork.common.account.PhoneType.PERSONAL;
import static com.getjavajob.training.timashovy.socialnetwork.common.account.PhoneType.WORKING;
import static com.getjavajob.training.timashovy.socialnetwork.service.util.singletonsregistry.ServiceSingletonRegistry.getInstance;
import static com.getjavajob.training.timashovy.socialnetwork.service.util.singletonsregistry.ServiceSingletonsNames.ACCOUNT_SERVICE_SINGLETON;
import static com.getjavajob.training.timashovy.socialnetwork.service.util.singletonsregistry.ServiceSingletonsNames.PHONE_SERVICE_SINGLETON;
import static com.getjavajob.training.timashovy.socialnetwork.web.util.JspDestinationPath.getJspPagePath;
import static java.lang.Long.valueOf;
import static java.time.LocalDate.parse;
import static java.util.Objects.isNull;

public class EditAccountServlet extends HttpServlet {

    private static final String FIRST_NAME_PARAMETER_NAME = "name";
    private static final String LAST_NAME_PARAMETER_NAME = "lastName";
    private static final String MIDDLE_NAME_PARAMETER_NAME = "middleName";
    private static final String BIRTHDATE_PARAMETER_NAME = "birthDate";
    private static final String SKYPE_PARAMETER_NAME = "skype";
    private static final String ICQ_PARAMETER_NAME = "icq";
    private static final String EMAIL_PARAMETER_NAME = "email";
    private static final String PASSWORD_PARAMETER_NAME = "password";
    private final AccountService accountService = getInstance().getSingleton(ACCOUNT_SERVICE_SINGLETON);
    private final PhoneServiceImpl phoneService = getInstance().getSingleton(PHONE_SERVICE_SINGLETON);

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
        Long accountId = valueOf(req.getParameter("id"));
        accountService.update(accountId, updateAccountData(req));
        resp.sendRedirect("/account?id=" + accountId);
    }

    private AccountUpdatingData updateAccountData(HttpServletRequest req) throws ServletException, IOException {
        return new AccountUpdatingData.Builder()
                .account(new Account.Builder()
                        .id(valueOf(req.getParameter("id")))
                        .avatar(req.getPart("avatar") != null && req.getPart("avatar").getSize() > 0
                                ? req.getPart("avatar").getInputStream() : null)
                        .firstName(req.getParameter(FIRST_NAME_PARAMETER_NAME))
                        .lastName(req.getParameter(LAST_NAME_PARAMETER_NAME))
                        .middleName(req.getParameter(MIDDLE_NAME_PARAMETER_NAME))
                        .birthDate(!isNull(req.getParameter(BIRTHDATE_PARAMETER_NAME))
                                && !req.getParameter(BIRTHDATE_PARAMETER_NAME).isEmpty()
                                ? parse(req.getParameter(BIRTHDATE_PARAMETER_NAME))
                                : null)
                        .skype(req.getParameter(SKYPE_PARAMETER_NAME))
                        .icq(req.getParameter(ICQ_PARAMETER_NAME))
                        .email(req.getParameter(EMAIL_PARAMETER_NAME))
                        .personalPhoneNumber(getUpdatedPhones(req, "personal", PERSONAL))
                        .workPhoneNumber(getUpdatedPhones(req, "working", WORKING))
                        .build())
                .password(req.getParameter(PASSWORD_PARAMETER_NAME))
                .build();
    }

    /**
     * Get updated phone numbers.
     * From servlet page we get array of phonesIds and array of new phoneValues.
     * There will be match between id and phoneValue. If there was no update, value will be empty.
     *
     * @param req request from servlet page
     * @return list of updated phones with new values or empty list if there were no updates
     */
    private List<Phone> getUpdatedPhones(HttpServletRequest req, String phoneTypeParam, PhoneType phoneType) {
        List<Phone> updatedPhones = new ArrayList<>();
        Long accountId = valueOf(req.getParameter("id"));
        String[] phonesIds = req.getParameterValues(phoneTypeParam + "PhoneId");
        String[] phoneValues = req.getParameterValues(phoneTypeParam + "PhoneValue");
        if (!isNull(phonesIds) && !isNull(phoneValues)) {
            for (int i = 0; i < phonesIds.length; i++) {
                if (!isNull(phonesIds[i]) && !phonesIds[i].isEmpty() && !phoneValues[i].isEmpty()) {
                    updatedPhones.add(new Phone(valueOf(phonesIds[i]), phoneType, phoneValues[i], accountId));
                }
            }
        }
        return updatedPhones;
    }

}
