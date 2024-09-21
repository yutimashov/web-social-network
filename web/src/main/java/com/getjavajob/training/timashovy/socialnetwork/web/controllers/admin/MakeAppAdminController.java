package com.getjavajob.training.timashovy.socialnetwork.web.controllers.admin;

import com.getjavajob.training.timashovy.socialnetwork.service.interfaces.AdminService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServlet;

@RequestMapping("/make-admin")
@Controller
public class MakeAppAdminController extends HttpServlet {

    private final AdminService adminService;

    public MakeAppAdminController(AdminService adminService) {
        this.adminService = adminService;
    }

    @GetMapping
    protected String doGet(@RequestParam("id") long id) {
        adminService.makeAdmin(id);
        return "redirect:/account?id=" + id;
    }

}
