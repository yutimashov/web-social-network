package com.getjavajob.dataprocessingservice.account.service;

import com.getjavajob.dataprocessingservice.account.service.exception.ServiceException;
import com.getjavajob.dataprocessingservice.account.web.feignclient.AccountClient;
import com.getjavajob.training.timashovy.socialnetwork.domain.account.Account;
import com.getjavajob.training.timashovy.socialnetwork.domain.account.AccountRole;
import com.getjavajob.training.timashovy.socialnetwork.domain.phone.Phone;
import com.getjavajob.training.timashovy.socialnetwork.domain.phone.PhoneType;
import org.slf4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.getjavajob.training.timashovy.socialnetwork.domain.phone.PhoneType.PERSONAL;
import static com.getjavajob.training.timashovy.socialnetwork.domain.phone.PhoneType.WORKING;
import static java.time.LocalDate.parse;
import static java.time.format.DateTimeFormatter.ofPattern;
import static java.util.Base64.getDecoder;
import static java.util.Optional.ofNullable;
import static javax.xml.transform.OutputKeys.INDENT;
import static org.slf4j.LoggerFactory.getLogger;

@Service
public class XmlDataHandlerImpl implements XmlDataHandler {

    private static final Logger logger = getLogger(XmlDataHandlerImpl.class);
    private final AccountClient accountClient;

    public XmlDataHandlerImpl(AccountClient accountClient) {
        this.accountClient = accountClient;
    }

    @Transactional
    @Override
    public byte[] loadAccountData(Account account) {
        try {
            Document document = DocumentBuilderFactory.newInstance().newDocumentBuilder().newDocument();
            populateDocumentWithAccountData(document, account);
            return transformDocumentToBytes(document);
        } catch (ParserConfigurationException e) {
            logger.error("Error generating XML: DocumentBuilder cannot be created", e);
            throw new ServiceException("Error generating XML document", e);
        } catch (TransformerException e) {
            logger.error("Error transforming document to XML for account={}", account.getId(), e);
            throw new ServiceException("Error transforming document to XML", e);
        }
    }

    private void populateDocumentWithAccountData(Document document, Account account) {
        Element root = document.createElement("account");
        document.appendChild(root);
        createElementWithText(document, root, "firstName", account.getFirstName());
        createElementWithText(document, root, "lastName", account.getLastName());
        createElementWithText(document, root, "middleName", account.getMiddleName());
        createElementWithText(document, root, "birthDate", account.getBirthDate().toString());
        createElementWithText(document, root, "personalAddress", account.getPersonalAddress());
        createElementWithText(document, root, "email", account.getEmail());
        createElementWithText(document, root, "icq", account.getIcq());
        createElementWithText(document, root, "skype", account.getSkype());
        appendPhonesToDocument(document, root, account);
    }

    private void appendPhonesToDocument(Document document, Element root, Account account) {
        Element phonesElement = document.createElement("phones");
        root.appendChild(phonesElement);
        Map<PhoneType, Element> phoneElements = new HashMap<>();
        phoneElements.put(PERSONAL, document.createElement("personalPhones"));
        phoneElements.put(WORKING, document.createElement("workingPhones"));
        for (Phone phone : account.getPhones()) {
            Element phoneElement = document.createElement("number");
            phoneElement.appendChild(document.createTextNode(phone.getNumber()));
            phoneElements.get(phone.getPhoneType()).appendChild(phoneElement);
        }
        for (Map.Entry<PhoneType, Element> entry : phoneElements.entrySet()) {
            if (entry.getValue().hasChildNodes()) {
                phonesElement.appendChild(entry.getValue());
            }
        }
    }

    private byte[] transformDocumentToBytes(Document document) throws TransformerException {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        Transformer transformer = TransformerFactory.newInstance().newTransformer();
        transformer.setOutputProperty(INDENT, "yes");
        transformer.setOutputProperty("{https://xml.apache.org/xslt}indent-amount", "4");
        transformer.transform(new DOMSource(document), new StreamResult(outputStream));
        return outputStream.toByteArray();
    }

    private void createElementWithText(Document document, Element parent, String name, String text) {
        if (text != null && !text.isEmpty()) {
            Element element = document.createElement(name);
            element.appendChild(document.createTextNode(text));
            parent.appendChild(element);
        }
    }

    @Transactional
    @Override
    public void updateAccount(InputStream inputStream, Long accountId) {
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
            accountClient.updateById(newAccount, accountId);
            logger.info("Xml update account with id={}. New account={}", accountId, newAccount);
        } catch (ParserConfigurationException | SAXException e) {
            logger.error("Error parsing XML for account with id={}", accountId, e);
            throw new ServiceException("Error parsing XML", e);
        } catch (IOException e) {
            logger.error("IO error while processing XML for account with id={}", accountId, e);
            throw new ServiceException("IO error during XML processing", e);
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
                                if (numberNode.getNodeType() == Node.ELEMENT_NODE && numberNode.getNodeName()
                                        .equals("number")) {
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
        ofNullable((String) accountPropertiesMap.get("lastName")).ifPresent(accountBuilder::lastName);
        ofNullable((String) accountPropertiesMap.get("middleName")).ifPresent(accountBuilder::middleName);
        ofNullable((String) accountPropertiesMap.get("birthDate")).map(date -> parse(date, ofPattern("yyyy-MM-dd")))
                .ifPresent(accountBuilder::birthDate);
        ofNullable((String) accountPropertiesMap.get("personalAddress")).ifPresent(accountBuilder::personalAddress);
        ofNullable((String) accountPropertiesMap.get("workAddress")).ifPresent(accountBuilder::workAddress);
        ofNullable((String) accountPropertiesMap.get("email")).ifPresent(accountBuilder::email);
        ofNullable((String) accountPropertiesMap.get("icq")).ifPresent(accountBuilder::icq);
        ofNullable((String) accountPropertiesMap.get("skype")).ifPresent(accountBuilder::skype);
        ofNullable((String) accountPropertiesMap.get("additionalInfo")).ifPresent(accountBuilder::additionalInfo);
        ofNullable((String) accountPropertiesMap.get("role")).map(role -> AccountRole.valueOf(role.toUpperCase()))
                .ifPresent(accountBuilder::role);
        ofNullable((String) accountPropertiesMap.get("avatar")).map(avatar -> getDecoder()
                .decode(avatar.replaceAll("\\s+", ""))).ifPresent(accountBuilder::avatar);
        ofNullable((List<Phone>) accountPropertiesMap.get("phones")).ifPresent(accountBuilder::phones);
        return accountBuilder.build();
    }

}
