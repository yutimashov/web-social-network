package com.getjavajob.training.timashovy.socialnetwork.service.serviceimpl.xmlhandler;

import com.getjavajob.training.timashovy.socialnetwork.dao.interfaces.account.AccountRepository;
import com.getjavajob.training.timashovy.socialnetwork.domain.account.Account;
import com.getjavajob.training.timashovy.socialnetwork.domain.account.AccountRole;
import com.getjavajob.training.timashovy.socialnetwork.domain.phone.Phone;
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
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

import static com.getjavajob.training.timashovy.socialnetwork.domain.phone.PhoneType.PERSONAL;
import static com.getjavajob.training.timashovy.socialnetwork.domain.phone.PhoneType.WORKING;
import static java.util.Optional.ofNullable;

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
            Map<String, Object> accountPropertiesMap = extractAccountProperties(document);
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

    private Map<String, Object> extractAccountProperties(Document document) {
        Element accountRootElement = document.getDocumentElement();
        NodeList accountPropertiesElements = accountRootElement.getChildNodes();
        Map<String, Object> accountPropertiesMap = new HashMap<>();
        List<Phone> phones = new ArrayList<>();
        for (int i = 0; i < accountPropertiesElements.getLength(); i++) {
            Node property = accountPropertiesElements.item(i);
            if (property.getNodeType() == Node.ELEMENT_NODE) {
                String nodeName = property.getNodeName();
                String textContent = property.getTextContent().trim();
                if (nodeName.equals("phones")) {
                    NodeList phoneTypes = property.getChildNodes();
                    for (int j = 0; j < phoneTypes.getLength(); j++) {
                        Node phoneTypeNode = phoneTypes.item(j);
                        if (phoneTypeNode.getNodeType() == Node.ELEMENT_NODE) {
                            String phoneTypeName = phoneTypeNode.getNodeName();
                            NodeList numbers = phoneTypeNode.getChildNodes();
                            for (int k = 0; k < numbers.getLength(); k++) {
                                Node numberNode = numbers.item(k);
                                if (numberNode.getNodeType() == Node.ELEMENT_NODE && numberNode.getNodeName().equals("number")) {
                                    String number = numberNode.getTextContent().trim();
                                    if (!number.isEmpty()) {
                                        if (phoneTypeName.equals("personalPhones")) {
                                            phones.add(new Phone(PERSONAL, number));
                                        } else if (phoneTypeName.equals("workingPhones")) {
                                            phones.add(new Phone(WORKING, number));
                                        }
                                    }
                                }
                            }
                        }
                    }
                } else {
                    if (!textContent.isEmpty()) {
                        accountPropertiesMap.put(nodeName, textContent);
                    }
                }
            }
        }
        accountPropertiesMap.put("phones", phones);
        return accountPropertiesMap;
    }

    @SuppressWarnings("unchecked")
    private Account generateAccount(Map<String, Object> accountPropertiesMap) {
        Account.Builder accountBuilder = new Account.Builder();
        ofNullable((String) accountPropertiesMap.get("firstName")).ifPresent(accountBuilder::firstName);
        ofNullable((String) accountPropertiesMap.get("lastName")).ifPresent(accountBuilder::lastName);
        ofNullable((String) accountPropertiesMap.get("middleName")).ifPresent(accountBuilder::middleName);
        ofNullable((String) accountPropertiesMap.get("birthDate"))
                .map(date -> LocalDate.parse(date, DateTimeFormatter.ofPattern("yyyy-MM-dd")))
                .ifPresent(accountBuilder::birthDate);
        ofNullable((String) accountPropertiesMap.get("personalAddress")).ifPresent(accountBuilder::personalAddress);
        ofNullable((String) accountPropertiesMap.get("workAddress")).ifPresent(accountBuilder::workAddress);
        ofNullable((String) accountPropertiesMap.get("email")).ifPresent(accountBuilder::email);
        ofNullable((String) accountPropertiesMap.get("icq")).ifPresent(accountBuilder::icq);
        ofNullable((String) accountPropertiesMap.get("skype")).ifPresent(accountBuilder::skype);
        ofNullable((String) accountPropertiesMap.get("additionalInfo")).ifPresent(accountBuilder::additionalInfo);
        ofNullable((String) accountPropertiesMap.get("role"))
                .map(role -> AccountRole.valueOf(role.toUpperCase()))
                .ifPresent(accountBuilder::role);
        ofNullable((String) accountPropertiesMap.get("avatar"))
                .map(avatar -> Base64.getDecoder().decode(avatar.replaceAll("\\s+", "")))
                .ifPresent(accountBuilder::avatar);
        // Добавление телефонов
        ofNullable((List<Phone>) accountPropertiesMap.get("phones")).ifPresent(accountBuilder::phones);
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
