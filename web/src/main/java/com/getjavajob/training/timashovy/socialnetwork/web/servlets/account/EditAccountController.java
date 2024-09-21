package com.getjavajob.training.timashovy.socialnetwork.web.servlets.account;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.getjavajob.training.timashovy.socialnetwork.common.account.Phone;
import com.getjavajob.training.timashovy.socialnetwork.service.interfaces.AccountService;
import com.getjavajob.training.timashovy.socialnetwork.service.interfaces.PhoneService;
import com.getjavajob.training.timashovy.socialnetwork.web.dto.AccountDto;
import com.getjavajob.training.timashovy.socialnetwork.web.mappers.AccountMapper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

import static com.getjavajob.training.timashovy.socialnetwork.common.account.PhoneType.PERSONAL;
import static com.getjavajob.training.timashovy.socialnetwork.common.account.PhoneType.WORKING;
import static com.getjavajob.training.timashovy.socialnetwork.web.util.ServiceSingletonsNames.PHONE_SERVICE_BEAN;
import static com.getjavajob.training.timashovy.socialnetwork.web.util.WebContextUtils.getApplicationContext;

@Controller
@RequestMapping("/account/edit")
public class EditAccountController {

    private final AccountService accountService;
    private final PhoneService phoneService;

    public EditAccountController(AccountService accountService, PhoneService phoneService) {
        this.accountService = accountService;
        this.phoneService = phoneService;
    }

    @GetMapping
    public String getEditAccountPage(Model model, @RequestParam("id") long accountId) {
        if (accountService.getById(accountId).isPresent()) {
            model.addAttribute("account", accountService.getById(accountId).get());
            model.addAttribute("avatarInputStream", accountService.getById(accountId).get().getAvatar());
            model.addAttribute("personalPhones", phoneService.getPersonalPhoneNumbers(accountId));
            model.addAttribute("workingPhones", phoneService.getWorkPhoneNumbers(accountId));
            return "account/edit";
        } else {
            return "error/404";
        }
    }

    @PostMapping
    public String doPost(@ModelAttribute AccountDto accountDto, @RequestParam("id") Long accountId,
                         HttpServletRequest req) throws IOException {
        accountService.update(accountId, new AccountMapper().toAccount(accountDto));
        addPhones(req, accountId);
        updatePhones(req);
        deletePhones(req);
        return "redirect:/account?id=" + accountId;
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
            phoneService.create(new Phone(PERSONAL, phoneNode.get("number").asText(), accountId));
        }
        for (JsonNode phoneNode : workingAddedNode) {
            phoneService.create(new Phone(WORKING, phoneNode.get("number").asText(), accountId));
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
