package dev.waterticket.jobdemo.user.controller;

import dev.waterticket.jobdemo.user.domain.User;
import dev.waterticket.jobdemo.user.dto.UserUpdateRequest;
import dev.waterticket.jobdemo.user.service.UserHistoryService;
import dev.waterticket.jobdemo.user.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/admin/user")
public class UserAdminController {
    private final UserService userService;
    private final UserHistoryService userHistoryService;

    public UserAdminController(UserService userService, UserHistoryService userHistoryService) {
        this.userService = userService;
        this.userHistoryService = userHistoryService;
    }

    @GetMapping
    public String memberList(Model model) {
        List<User> users = this.userService.getUserAll();
        model.addAttribute("users", users);
        return "admin/user/list";
    }

    @GetMapping("/{idx}")
    public String memberDetail(Model model, @PathVariable("idx") final Integer idx) {
        User user = this.userService.getUserByIdx(idx);
        model.addAttribute("user", user);
        return "admin/user/detail";
    }

    @GetMapping("/{idx}/edit")
    public String memberEditView(Model model, @PathVariable("idx") final Integer idx) {
        User user = this.userService.getUserByIdx(idx);
        model.addAttribute("user", user);
        return "admin/user/edit";
    }

    @PutMapping("/{idx}/edit")
    public String memberEdit(@Valid @ModelAttribute final UserUpdateRequest userUpdateRequest, @PathVariable("idx") final Integer idx, HttpServletRequest request) {
        User user = this.userService.update(idx, userUpdateRequest);
        this.userHistoryService.updateUser(user.getIdx(), request.getRequestURI(), request.getRemoteAddr());
        return "redirect:/admin/user/{idx}";
    }

    @GetMapping("/{idx}/delete")
    public String memberDeleteView(Model model, @PathVariable("idx") final Integer idx) {
        User user = this.userService.getUserByIdx(idx);
        model.addAttribute("user", user);
        return "admin/user/delete";
    }

    @DeleteMapping("/{idx}/delete")
    public String memberDelete(@PathVariable("idx") final Integer idx, HttpServletRequest request) {
        User user = this.userService.getUserByIdx(idx);
        this.userService.delete(user.getId());
        this.userHistoryService.deleteUser(user.getIdx(), request.getRequestURI(), request.getRemoteAddr());
        return "redirect:/admin/user";
    }
}
