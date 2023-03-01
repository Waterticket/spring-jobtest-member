package dev.waterticket.jobdemo.admin.controller;

import dev.waterticket.jobdemo.user.domain.User;
import dev.waterticket.jobdemo.user.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;


@Controller
@RequestMapping("/admin")
public class AdminController {
    private final UserService userService;

    public AdminController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public String adminIndex() {
        return "redirect:/admin/user";
    }

    @GetMapping("/user")
    public String memberList(Model model) {
        List<User> users = this.userService.getUserAll();
        model.addAttribute("users", users);
        return "admin/user/list";
    }
}
