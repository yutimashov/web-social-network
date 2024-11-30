package com.getjavajob.training.timashovy.socialnetwork.service.serviceimpl.xmlhandler;

import com.getjavajob.training.timashovy.socialnetwork.dao.interfaces.account.AccountRepository;
import com.getjavajob.training.timashovy.socialnetwork.domain.account.Account;
import com.getjavajob.training.timashovy.socialnetwork.domain.account.AccountRole;
import com.getjavajob.training.timashovy.socialnetwork.service.interfaces.XmlDataHandler;
import com.getjavajob.training.timashovy.socialnetwork.service.util.exceptions.ServiceException;
import org.springframework.transaction.annotation.Transactional;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

import static javax.xml.parsers.DocumentBuilderFactory.newInstance;

public class XmlDataHandlerImpl implements XmlDataHandler {

    private final AccountRepository accountDao;

    public XmlDataHandlerImpl(AccountRepository accountDao) {
        this.accountDao = accountDao;
    }

    @Transactional
    @Override
    public void updateAccount(InputStream inputStream, Long accountId) {
        DocumentBuilderFactory documentBuilderFactory = newInstance();
        DocumentBuilder builder = null;
        try {
            builder = documentBuilderFactory.newDocumentBuilder();
        } catch (ParserConfigurationException e) {
            throw new ServiceException(e.getMessage(), e.getCause());
        }
        Document document = null;
        try {
            document = builder.parse(inputStream);
        } catch (SAXException | IOException e) {
            throw new ServiceException(e.getMessage(), e.getCause());
        }
        Element xmlAccount = document.getDocumentElement();
        NodeList xmlAccountProperties = xmlAccount.getChildNodes();
        Map<String, String> accountPropertiesMap = new HashMap<>();
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        for (int i = 0; i < xmlAccountProperties.getLength(); i++) {
            Node property = xmlAccountProperties.item(i);
            if (!property.getNodeName().equals("#text") && !property.getNodeName().equals("#comment")
                    && !property.getTextContent().isEmpty()) {
                accountPropertiesMap.put(property.getNodeName(), property.getTextContent());
            }
        }
        Account account = new Account.Builder()
                .firstName(accountPropertiesMap.getOrDefault("firstName", null))
                .lastName(accountPropertiesMap.getOrDefault("lastName", null))
                .middleName(accountPropertiesMap.getOrDefault("middleName", null))
                .birthDate(accountPropertiesMap.getOrDefault("birthDate", null) == null
                        ? null : LocalDate.parse(accountPropertiesMap.get("birthDate"), dateFormatter))
                .personalAddress(accountPropertiesMap.getOrDefault("personalAddress", null))
                .workAddress(accountPropertiesMap.getOrDefault("workAddress", null))
                .email(accountPropertiesMap.getOrDefault("email", null))
                .icq(accountPropertiesMap.getOrDefault("icq", null))
                .skype(accountPropertiesMap.getOrDefault("skype", null))
                .additionalInfo(accountPropertiesMap.getOrDefault("additionalInfo", null))
                .role(accountPropertiesMap.getOrDefault("role", null) == null
                        ? null : AccountRole.valueOf(accountPropertiesMap.get("role").toUpperCase()))
                .avatar(accountPropertiesMap.getOrDefault("avatar", null) == null
                        ? null : Base64.getDecoder().decode(accountPropertiesMap.get("avatar").replaceAll("\\s+", "")))
                .build();
        accountDao.updateById(account, accountId);
        System.out.println(account);
    }

    @Transactional
    @Override
    public Document downloadAccountInfo(Account account) throws ParserConfigurationException {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document document = builder.newDocument();
        // Создание корневого элемента
        Element root = document.createElement("account");
        document.appendChild(root);
        // Создание дочерних элементов с данными
        Element firstName = document.createElement("firstName");
        firstName.appendChild(document.createTextNode(account.getFirstName()));
        root.appendChild(firstName);
        Element email = document.createElement("email");
        email.appendChild(document.createTextNode(account.getEmail()));
        root.appendChild(email);
        return document;
    }

}
