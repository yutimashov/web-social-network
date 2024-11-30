package com.getjavajob.training.timashovy.socialnetwork.service.interfaces;

import com.getjavajob.training.timashovy.socialnetwork.domain.account.Account;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.io.InputStream;

public interface XmlDataHandler {

    void updateAccount(InputStream inputStream, Long accountId) throws ParserConfigurationException, IOException, SAXException;

    Document downloadAccountInfo(Account account) throws ParserConfigurationException;

}
