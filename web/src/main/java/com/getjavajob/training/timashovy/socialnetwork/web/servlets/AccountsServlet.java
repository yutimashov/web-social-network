package com.getjavajob.training.timashovy.socialnetwork.web.servlets;

import com.getjavajob.training.timashovy.socialnetwork.common.Account;
import com.getjavajob.training.timashovy.socialnetwork.service.serviceimpl.AccountServiceImpl;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import static com.getjavajob.training.timashovy.socialnetwork.service.serviceimpl.AccountServiceImpl.getAccountServiceInstance;

public class AccountsServlet extends HttpServlet {

    private final AccountServiceImpl accountService = getAccountServiceInstance();

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
                    printWriter.write("<p>Личный номер телефона: " + account.getPersonalPhoneNumber() + " </p>");
                    printWriter.write("<p>Рабочий номер телефона: " + account.getWorkPhoneNumber() + " </p>");
                    printWriter.write("<p>Домашний адрес: " + account.getPersonalAddress() + " </p>");
                    printWriter.write("</li>");
                }
                printWriter.write("</ul");
            }
        }
    }

}
