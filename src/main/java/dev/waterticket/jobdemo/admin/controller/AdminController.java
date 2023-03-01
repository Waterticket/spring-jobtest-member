package dev.waterticket.jobdemo.admin.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/admin")
public class AdminController {
    @GetMapping
    public String adminIndex() {
        return "redirect:/admin/user";
    }
}
