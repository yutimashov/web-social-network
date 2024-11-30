package com.getjavajob.training.timashovy.socialnetwork.service.serviceimpl.xmlhandler;

import com.getjavajob.training.timashovy.socialnetwork.dao.interfaces.account.AccountRepository;
import com.getjavajob.training.timashovy.socialnetwork.domain.account.Account;
import com.getjavajob.training.timashovy.socialnetwork.domain.account.AccountRole;
import com.getjavajob.training.timashovy.socialnetwork.service.interfaces.XmlDataHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
import java.util.HashMap;
import java.util.Map;

import static java.time.LocalDate.parse;
import static java.time.format.DateTimeFormatter.ofPattern;
import static java.util.Base64.getDecoder;
import static java.util.Optional.ofNullable;
import static org.w3c.dom.Node.ELEMENT_NODE;

public class XmlDataHandlerImpl implements XmlDataHandler {

    private final AccountRepository accountDao;
    private static final Logger logger = LoggerFactory.getLogger(XmlDataHandlerImpl.class);

    public XmlDataHandlerImpl(AccountRepository accountDao) {
        this.accountDao = accountDao;
    }

    @Transactional
    @Override
    public void updateAccount(InputStream inputStream, Long accountId) throws ParserConfigurationException,
            IOException, SAXException {
        if (inputStream == null) {
            throw new IllegalArgumentException("InputStream cannot be null");
        }
        if (accountId == null) {
            throw new IllegalArgumentException("AccountId cannot be null");
        }
        try (InputStream is = inputStream) {
            Document document = parseXml(is);
            Map<String, String> accountPropertiesMap = extractAccountProperties(document);
            Account newAccount = generateAccount(accountPropertiesMap);
            accountDao.updateById(newAccount, accountId);
            logger.info("Xml update account with id={}. New account={}", accountId, newAccount);
        }
    }

    private Document parseXml(InputStream inputStream)
            throws ParserConfigurationException, IOException, SAXException {
        return DocumentBuilderFactory.newInstance()
                .newDocumentBuilder()
                .parse(inputStream);
    }

    private Map<String, String> extractAccountProperties(Document document) {
        Element accountRootElement = document.getDocumentElement();
        NodeList accountPropertiesElements = accountRootElement.getChildNodes();
        Map<String, String> accountPropertiesMap = new HashMap<>();
        for (int i = 0; i < accountPropertiesElements.getLength(); i++) {
            Node property = accountPropertiesElements.item(i);
            if (property.getNodeType() == ELEMENT_NODE) {
                String textContent = property.getTextContent().trim();
                if (!textContent.isEmpty()) {
                    accountPropertiesMap.put(property.getNodeName(), textContent);
                }
            }
        }
        return accountPropertiesMap;
    }

    private Account generateAccount(Map<String, String> accountPropertiesMap) {
        Account.Builder accountBuilder = new Account.Builder();
        ofNullable(accountPropertiesMap.get("firstName")).ifPresent(accountBuilder::firstName);
        ofNullable(accountPropertiesMap.get("lastName")).ifPresent(accountBuilder::lastName);
        ofNullable(accountPropertiesMap.get("middleName")).ifPresent(accountBuilder::middleName);
        ofNullable(accountPropertiesMap.get("birthDate"))
                .map(date -> parse(date, ofPattern("yyyy-MM-dd"))).ifPresent(accountBuilder::birthDate);
        ofNullable(accountPropertiesMap.get("personalAddress")).ifPresent(accountBuilder::personalAddress);
        ofNullable(accountPropertiesMap.get("email")).ifPresent(accountBuilder::email);
        ofNullable(accountPropertiesMap.get("icq")).ifPresent(accountBuilder::icq);
        ofNullable(accountPropertiesMap.get("skype")).ifPresent(accountBuilder::skype);
        ofNullable(accountPropertiesMap.get("role"))
                .map(role -> AccountRole.valueOf(role.toUpperCase()))
                .ifPresent(accountBuilder::role);
        ofNullable(accountPropertiesMap.get("avatar"))
                .map(avatar -> getDecoder().decode(avatar.replaceAll("\\s+", "")))
                .ifPresent(accountBuilder::avatar);
        return accountBuilder.build();
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
