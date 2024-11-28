package com.getjavajob.training.timashovy.socialnetwork.web.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.getjavajob.training.timashovy.socialnetwork.domain.account.Account;
import com.getjavajob.training.timashovy.socialnetwork.domain.phone.Phone;
import com.getjavajob.training.timashovy.socialnetwork.service.interfaces.AccountService;
import com.getjavajob.training.timashovy.socialnetwork.service.interfaces.AdminService;
import com.getjavajob.training.timashovy.socialnetwork.service.interfaces.MessageService;
import com.getjavajob.training.timashovy.socialnetwork.service.interfaces.PhoneService;
import com.getjavajob.training.timashovy.socialnetwork.web.dto.AccountDto;
import com.getjavajob.training.timashovy.socialnetwork.web.mappers.AccountMapper;
import com.getjavajob.training.timashovy.socialnetwork.web.util.exceptions.WebException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.servlet.http.HttpServletRequest;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Objects;
import java.util.Optional;

import static com.getjavajob.training.timashovy.socialnetwork.domain.phone.PhoneType.PERSONAL;
import static com.getjavajob.training.timashovy.socialnetwork.domain.phone.PhoneType.WORKING;
import static com.getjavajob.training.timashovy.socialnetwork.web.util.UrlStatusParameter.DELETE_ACCOUNT_SUCCESS;

@Controller
@RequestMapping("/account")
public class AccountController {

    private final AccountService accountService;
    private final MessageService messageService;
    private final PhoneService phoneService;
    private final AdminService adminService;

    public AccountController(AccountService accountService, MessageService messageService, PhoneService phoneService,
                             AdminService adminService) {
        this.accountService = accountService;
        this.messageService = messageService;
        this.phoneService = phoneService;
        this.adminService = adminService;
    }

    @GetMapping
    public String account(@RequestParam("id") Long accountId,
                          @SessionAttribute Account account,
                          Model model) {
        Optional<Account> maybeAccount = accountService.getById(accountId);
        if (maybeAccount.isPresent()) {
            if (accountService.checkFriendshipRecordExistence(account.getId(), accountId)) {
                model.addAttribute("alreadySentFriendRequest", true);
            }
            model.addAttribute("account", maybeAccount.get());
            model.addAttribute("wallPosts", messageService.getAllAccountWallMessages(accountId));
            model.addAttribute("accountService", accountService);
            model.addAttribute("personalPhones", phoneService.getPhoneNumbers(accountId, PERSONAL));
            model.addAttribute("workingPhones", phoneService.getPhoneNumbers(accountId, WORKING));
            return "account/account";
        } else {
            return "error/404";
        }
    }

    @GetMapping("/all")
    public String allAccounts(Model model) {
        model.addAttribute("accounts", accountService.getAll());
        return "account/all";
    }

    @GetMapping("/delete")
    public String deleteAccount(@RequestParam("id") long id,
                                @SessionAttribute Account account) {
        Long accountToDeleteId = id;
        accountService.delete(accountToDeleteId);
        if (!Objects.equals(account.getId(), accountToDeleteId)) {
            return "redirect:/account/all";
        } else {
            return "redirect:/login" + DELETE_ACCOUNT_SUCCESS.getValue();
        }
    }

    @GetMapping("/make-admin")
    public String makeAdmin(@RequestParam("id") long id) {
        adminService.makeAdmin(id);
        return "redirect:/account?id=" + id;
    }

    @GetMapping("/edit")
    public String edit(Model model, @RequestParam("id") long accountId) {
        if (accountService.getById(accountId).isPresent()) {
            model.addAttribute("account", accountService.getById(accountId).get());
            model.addAttribute("avatarInputStream", accountService.getById(accountId).get().getAvatar());
            model.addAttribute("personalPhones", phoneService.getPhones(accountId, PERSONAL));
            model.addAttribute("workingPhones", phoneService.getPhones(accountId, WORKING));
            return "account/edit";
        } else {
            return "error/404";
        }
    }

    @PostMapping("/edit")
    public String update(@ModelAttribute AccountDto accountDto,
                         @RequestParam("id") Long accountId,
                         HttpServletRequest req) {
        accountService.update(accountId, new AccountMapper().toAccount(accountDto));
        addPhones(req, accountId);
        updatePhones(req);
        deletePhones(req);
        return "redirect:/account?id=" + accountId;
    }

    @PostMapping("/xml-update")
    public String updateXml(@RequestParam("file") MultipartFile file,
                            @RequestParam("id") Long accountId,
                            HttpServletRequest req) {
//        accountService.update(accountId, new AccountMapper().toAccount(accountDto));
//        addPhones(req, accountId);
//        updatePhones(req);
//        deletePhones(req);
        if (file.isEmpty()) {
            // no file is sent or empty file is sent
        }
        if (file.getSize() > 1024) {
            // if file size > 1 mb
        }
        try {
            accountService.xmlFileUpdateAccount(file.getInputStream());
        } catch (IOException e) {
            throw new WebException(e.getMessage(), e.getCause());
        }
        return "redirect:/account?id=" + accountId;
    }

    @GetMapping("/xml-download")
    public ResponseEntity<byte[]> downloadAccountXml(@ModelAttribute AccountDto accountDto,
                                   @RequestParam("id") Long accountId) {
        try {
            // Здесь должна быть логика получения данных Account по accountId
            Account account = new AccountMapper().toAccount(accountDto);

            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document document = builder.newDocument();

            // Создание корневого элемента
            Element root = document.createElement("Account");
            document.appendChild(root);

            // Создание дочерних элементов с данными
            Element username = document.createElement("firstName");
            username.appendChild(document.createTextNode(account.getFirstName()));
            root.appendChild(username);

            Element email = document.createElement("email");
            email.appendChild(document.createTextNode(account.getEmail()));
            root.appendChild(email);

            DOMSource source = new DOMSource(document);
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            StreamResult result = new StreamResult(outputStream);

            // Создание Transformer для преобразования XML в поток
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            transformer.setOutputProperty("{https://xml.apache.org/xslt}indent-amount", "4");
            transformer.transform(source, result);

            // Установка заголовков для скачивания файла
            HttpHeaders headers = new HttpHeaders();
            headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=account.xml");
            headers.add(HttpHeaders.CONTENT_TYPE, "application/xml");

            return new ResponseEntity<>(outputStream.toByteArray(), headers, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    private void deletePhones(HttpServletRequest req) {
        JsonNode rootNode = getRootNode(req);
        JsonNode deletedNode = rootNode.get("deletedPhonesIds");
        for (int i = 0; i < deletedNode.size(); i++) {
            phoneService.delete(deletedNode.get(i).asLong());
        }
    }

    private void updatePhones(HttpServletRequest req) {
        JsonNode rootNode = getRootNode(req);
        JsonNode updatedNode = rootNode.get("updated");
        for (JsonNode phoneNode : updatedNode) {
            phoneService.update(phoneNode.get("id").asLong(), phoneNode.get("number").asText());
        }
    }

    private void addPhones(HttpServletRequest req, Long accountId) {
        JsonNode rootNode = getRootNode(req);
        JsonNode addedNode = rootNode.get("added");
        JsonNode personalAddedNode = addedNode.get("personal");
        JsonNode workingAddedNode = addedNode.get("working");
        for (JsonNode phoneNode : personalAddedNode) {
            phoneService.create(new Phone(PERSONAL, phoneNode.get("number").asText(), accountService.getById(accountId).get()));
        }
        for (JsonNode phoneNode : workingAddedNode) {
            phoneService.create(new Phone(WORKING, phoneNode.get("number").asText(), accountService.getById(accountId).get()));
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
