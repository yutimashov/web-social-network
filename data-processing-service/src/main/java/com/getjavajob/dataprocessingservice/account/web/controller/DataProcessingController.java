package com.getjavajob.dataprocessingservice.account.web.controller;

import com.getjavajob.dataprocessingservice.account.service.XmlDataHandler;
import com.getjavajob.dataprocessingservice.account.service.exception.ServiceException;
import com.getjavajob.dataprocessingservice.account.web.exceptions.WebException;
import com.getjavajob.dataprocessingservice.account.web.feignclient.AccountClient;
import com.getjavajob.training.timashovy.socialnetwork.domain.account.Account;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Optional;

import static org.springframework.http.HttpHeaders.CONTENT_DISPOSITION;
import static org.springframework.http.HttpHeaders.CONTENT_TYPE;
import static org.springframework.http.HttpStatus.NOT_FOUND;
import static org.springframework.http.HttpStatus.OK;

@Controller
public class DataProcessingController {

    private final XmlDataHandler xmlDataHandler;
    private final AccountClient accountClient;

    public DataProcessingController(XmlDataHandler xmlDataHandler, AccountClient accountClient) {
        this.xmlDataHandler = xmlDataHandler;
        this.accountClient = accountClient;
    }

    @PostMapping("/xml-update")
    public void updateXml(@RequestParam("file") MultipartFile file,
                          @RequestParam("id") Long accountId,
                          HttpServletResponse response,
                          HttpServletRequest request) throws IOException {
        try {
            xmlDataHandler.updateAccount(file.getInputStream(), accountId);
        } catch (IOException e) {
            throw new WebException("Failed to process the file. Please try again later.");
        } catch (ServiceException e) {
            throw new WebException("An error occurred while processing the account data. Please try again later.");
        }
        response.sendRedirect(generateRedirectBaseURL(request, accountId));
    }

    private String generateRedirectBaseURL(HttpServletRequest request, Long accountId) {
        String scheme = request.getScheme();
        String serverName = request.getHeader("X-Forwarded-Host");
        if (serverName == null || serverName.isEmpty()) {
            serverName = request.getServerName();
        }
        return scheme + "://" + serverName + "/account/edit?id=" + accountId;
    }

    @GetMapping("/xml-download")
    public ResponseEntity<byte[]> downloadAccountXml(@RequestParam("id") Long accountId) {
        Optional<Account> maybeAccount = accountClient.accountById(accountId).getBody();
        if (maybeAccount.isPresent()) {
            try {
                byte[] accountData = xmlDataHandler.loadAccountData(maybeAccount.get());
                HttpHeaders headers = new HttpHeaders();
                headers.add(CONTENT_DISPOSITION, "attachment; filename=account.xml");
                headers.add(CONTENT_TYPE, "application/xml");
                return new ResponseEntity<>(accountData, headers, OK);
            } catch (ServiceException e) {
                throw new WebException("An error occurred while processing account data to xml file. "
                        + "Please try again later.");
            }
        } else {
            return new ResponseEntity<>(NOT_FOUND);
        }
    }

}
