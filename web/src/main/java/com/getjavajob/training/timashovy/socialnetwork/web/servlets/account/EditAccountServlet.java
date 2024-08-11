package com.getjavajob.training.timashovy.socialnetwork.web.servlets.account;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.getjavajob.training.timashovy.socialnetwork.common.account.Account;
import com.getjavajob.training.timashovy.socialnetwork.common.account.Phone;
import com.getjavajob.training.timashovy.socialnetwork.service.interfaces.AccountService;
import com.getjavajob.training.timashovy.socialnetwork.service.interfaces.PhoneService;
import org.springframework.context.ApplicationContext;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.getjavajob.training.timashovy.socialnetwork.common.account.PhoneType.PERSONAL;
import static com.getjavajob.training.timashovy.socialnetwork.common.account.PhoneType.WORKING;
import static com.getjavajob.training.timashovy.socialnetwork.web.util.JspDestinationPath.getJspPagePath;
import static com.getjavajob.training.timashovy.socialnetwork.web.util.ServiceSingletonsNames.ACCOUNT_SERVICE_BEAN;
import static com.getjavajob.training.timashovy.socialnetwork.web.util.ServiceSingletonsNames.PHONE_SERVICE_BEAN;
import static com.getjavajob.training.timashovy.socialnetwork.web.util.WebContextUtils.getApplicationContext;
import static java.lang.Long.valueOf;
import static java.time.LocalDate.parse;
import static java.util.Objects.isNull;

//TODO: add exception. Add server error to front
public class EditAccountServlet extends HttpServlet {

    private static final String FIRST_NAME_PARAMETER_NAME = "name";
    private static final String LAST_NAME_PARAMETER_NAME = "lastName";
    private static final String MIDDLE_NAME_PARAMETER_NAME = "middleName";
    private static final String BIRTHDATE_PARAMETER_NAME = "birthDate";
    private static final String SKYPE_PARAMETER_NAME = "skype";
    private static final String ICQ_PARAMETER_NAME = "icq";
    private static final String EMAIL_PARAMETER_NAME = "email";
    private static final String AVATAR_PARAMETER_NAME = "avatar";

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Long accountId = valueOf(req.getParameter("id"));
        ApplicationContext ctx = getApplicationContext(req.getServletContext());
        AccountService accountService = ctx.getBean(ACCOUNT_SERVICE_BEAN, AccountService.class);
        if (accountService.getById(accountId).isPresent()) {
            req.setAttribute("account", accountService.getById(accountId).get());
            req.setAttribute("avatarInputStream", accountService.getById(accountId).get().getAvatar());
            PhoneService phoneService = ctx.getBean(PHONE_SERVICE_BEAN, PhoneService.class);
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
        getApplicationContext(req.getServletContext()).getBean(ACCOUNT_SERVICE_BEAN, AccountService.class)
                .update(accountId, getUpdatedAccountData(req));
        addPhones(req, accountId);
        updatePhones(req);
        deletePhones(req);
        resp.sendRedirect("/account?id=" + accountId);
    }

    private Account getUpdatedAccountData(HttpServletRequest req) throws ServletException, IOException {
        return new Account.Builder()
                .id(valueOf(req.getParameter("id")))
                .avatar(req.getPart(AVATAR_PARAMETER_NAME) != null
                        && req.getPart(AVATAR_PARAMETER_NAME).getSize() > 0
                        ? req.getPart(AVATAR_PARAMETER_NAME).getInputStream() : null)
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
                .build();
    }

    private void deletePhones(HttpServletRequest req) {
        JsonNode rootNode = getRootNode(req);
        JsonNode deletedNode = rootNode.get("deletedPhonesIds");
        PhoneService phoneService = getApplicationContext(req.getServletContext()).getBean(PHONE_SERVICE_BEAN,
                PhoneService.class);
        for (int i = 0; i < deletedNode.size(); i++) {
            phoneService.deleteById(deletedNode.get(i).asLong());
        }
    }

    private void updatePhones(HttpServletRequest req) {
        JsonNode rootNode = getRootNode(req);
        JsonNode updatedNode = rootNode.get("updated");
        PhoneService phoneService = getApplicationContext(req.getServletContext()).getBean(PHONE_SERVICE_BEAN,
                PhoneService.class);
        for (JsonNode phoneNode : updatedNode) {
            phoneService.update(phoneNode.get("id").asLong(), phoneNode.get("number").asText());
        }
    }

    private void addPhones(HttpServletRequest req, Long accountId) {
        JsonNode rootNode = getRootNode(req);
        JsonNode addedNode = rootNode.get("added");
        PhoneService phoneService = getApplicationContext(req.getServletContext()).getBean(PHONE_SERVICE_BEAN,
                PhoneService.class);
        JsonNode personalAddedNode = addedNode.get("personal");
        JsonNode workingAddedNode = addedNode.get("working");
        for (JsonNode phoneNode : personalAddedNode) {
            phoneService.create(new Phone(PERSONAL, phoneNode.asText(), accountId));
        }
        for (JsonNode phoneNode : workingAddedNode) {
            phoneService.create(new Phone(WORKING, phoneNode.asText(), accountId));
        }
    }

    private JsonNode getRootNode(HttpServletRequest req) {
        String phonesJSON = req.getParameter("phoneData");
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            return objectMapper.readTree(phonesJSON);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

}
