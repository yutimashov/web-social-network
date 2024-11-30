package com.getjavajob.training.timashovy.socialnetwork.service.interfaces;

import com.getjavajob.training.timashovy.socialnetwork.domain.account.Account;
import org.w3c.dom.Document;

import javax.xml.parsers.ParserConfigurationException;
import java.io.InputStream;

public interface XmlDataHandler {

    void updateAccount(InputStream inputStream, Long accountId);

    Document downloadAccountInfo(Account account) throws ParserConfigurationException;

}
