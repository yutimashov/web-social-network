package com.getjavajob.training.timashovy.socialnetwork.web.servlets.friendship;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServlet;

@Controller
@RequestMapping("/friends/requests")
public class ShowAllRequestsController extends HttpServlet {

    @GetMapping
    protected String doGet() {
        return "friendship/requests/requests";
    }

}
