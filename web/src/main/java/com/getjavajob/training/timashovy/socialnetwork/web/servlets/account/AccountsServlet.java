package com.getjavajob.training.timashovy.socialnetwork.web.servlets.account;

import com.getjavajob.training.timashovy.socialnetwork.common.account.Account;
import com.getjavajob.training.timashovy.socialnetwork.common.account.Phone;
import com.getjavajob.training.timashovy.socialnetwork.service.serviceimpl.AccountServiceImpl;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import static com.getjavajob.training.timashovy.socialnetwork.service.serviceimpl.AccountServiceImpl.getInstance;

public class AccountsServlet extends HttpServlet {

    private final AccountServiceImpl accountService = getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setContentType("text/html");
        resp.setCharacterEncoding("UTF-8");
        try (PrintWriter printWriter = resp.getWriter()) {
            printWriter.write("<h1>Список аккаунтов</h1>");
            List<Account> accounts = accountService.getAllAccounts();
            if (accounts.isEmpty()) {
                printWriter.write("<p>Аккаунтов не найдено</p>");
            } else {
                printWriter.write("<p>Найдено: " + accounts.size() + " аккаунтов.</p>");
                printWriter.write("<ul>");
                for (Account account : accounts) {
                    printWriter.write("<li>");
                    printWriter.write("<p>Имя: " + account.getFirstName() + " </p>");
                    printWriter.write("<p>Фамилия: " + account.getLastName() + " </p>");
                    printWriter.write("<p>Личный номер телефона: " + getPhoneNumber(account.getPersonalPhoneNumber())
                            + " </p>");
                    printWriter.write("<p>Рабочий номер телефона: " + getPhoneNumber(account.getWorkPhoneNumber())
                            + " </p>");
                    printWriter.write("<p>Домашний адрес: " + account.getPersonalAddress() + " </p>");
                    printWriter.write("</li>");
                }
                printWriter.write("</ul");
            }
        }
    }

    private String getPhoneNumber(List<Phone> phones) {
        StringBuilder sb = new StringBuilder();
        for (Phone phone : phones) {
            sb.append(phone.getNumber()).append(", ");
        }
        String str = sb.toString().trim();
        if (str.length() > 0 && str.charAt(str.length() - 1) == ',') {
            str = str.substring(0, str.length() - 1);
        }
        return str;
    }

}
