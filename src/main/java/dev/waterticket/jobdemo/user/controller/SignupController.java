package dev.waterticket.jobdemo.user.controller;

import dev.waterticket.jobdemo.user.dto.UserSignupRequest;
import dev.waterticket.jobdemo.user.exception.SameIDExistsException;
import dev.waterticket.jobdemo.user.service.UserService;
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

    public SignupController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public String signup(@ModelAttribute UserSignupRequest userSignupRequest) {
        return "signup";
    }

    @PostMapping
    public String signupPost(@Valid @ModelAttribute UserSignupRequest userSignupRequest, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "signup";
        }

        try{
            this.userService.insert(userSignupRequest.toEntity());
        } catch (SameIDExistsException e) {
            bindingResult.addError(new ObjectError("id", "이미 존재하는 아이디입니다."));
            return "signup";
        }

        return "redirect:/login";
    }
}
