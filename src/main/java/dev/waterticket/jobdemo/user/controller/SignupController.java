package dev.waterticket.jobdemo.user.controller;

import dev.waterticket.jobdemo.user.domain.User;
import dev.waterticket.jobdemo.user.dto.UserSignupRequest;
import dev.waterticket.jobdemo.user.exception.SameIDExistsException;
import dev.waterticket.jobdemo.user.service.UserHistoryService;
import dev.waterticket.jobdemo.user.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/signup")
public class SignupController {
    private final UserService userService;
    private final UserHistoryService userHistoryService;

    public SignupController(UserService userService, UserHistoryService userHistoryService) {
        this.userService = userService;
        this.userHistoryService = userHistoryService;
    }

    @GetMapping
    public String signup(@ModelAttribute final UserSignupRequest userSignupRequest) {
        return "signup";
    }

    @PostMapping
    public String signupPost(@Valid @ModelAttribute final UserSignupRequest userSignupRequest, BindingResult bindingResult, HttpServletRequest request) {
        if (bindingResult.hasErrors()) {
            return "signup";
        }

        try{
            User user = this.userService.insert(userSignupRequest.toEntity());
            this.userHistoryService.createUser(user.getIdx(), request.getRequestURI(), request.getRemoteAddr());
        } catch (SameIDExistsException e) {
            bindingResult.addError(new ObjectError("id", "이미 존재하는 아이디입니다."));
            return "signup";
        }

        return "redirect:/login";
    }
}
