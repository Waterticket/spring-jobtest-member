package dev.waterticket.jobdemo.user.controller;

import dev.waterticket.jobdemo.user.domain.User;
import dev.waterticket.jobdemo.user.dto.UserAddRequest;
import dev.waterticket.jobdemo.user.dto.UserResponse;
import dev.waterticket.jobdemo.user.exception.ParameterException;
import dev.waterticket.jobdemo.user.exception.SameIDExistsException;
import dev.waterticket.jobdemo.user.exception.UserNotFoundException;
import dev.waterticket.jobdemo.user.service.UserService;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;

import java.util.List;

@RestController
@RequestMapping("/api/user")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public List<UserResponse> getMemberList() {
        List<User> users = this.userService.getUserAll();
        return UserResponse.listOf(users);
    }


    @GetMapping("/idx/{idx}")
    public UserResponse getMemberByIdx(@PathVariable("idx") Integer idx) {
        User user = this.userService.getUserByIdx(idx);
        return UserResponse.builder()
                .idx(user.getIdx())
                .id(user.getId())
                .name(user.getName())
                .auth(user.getAuth())
                .build();
    }

    @GetMapping("/id/{id}")
    public UserResponse getMemberById(@PathVariable("id") String id) {
        User user = this.userService.getUserById(id);
        return UserResponse.builder()
                .idx(user.getIdx())
                .id(user.getId())
                .name(user.getName())
                .auth(user.getAuth())
                .build();
    }

    @GetMapping("/name/{name}")
    public List<UserResponse> getMemberByName(@PathVariable("name") String name) {
        List<User> users = this.userService.getUserByName(name);
        return UserResponse.listOf(users);
    }

    @PostMapping
    public UserResponse insertMember(@RequestBody @Valid final UserAddRequest userAddRequest) {
        try {
            User user = this.userService.getUserById(userAddRequest.getId());
            if (user != null) {
                throw new SameIDExistsException();
            }
        } catch (UserNotFoundException e) {
            // do nothing
        }

        try {
            User newUser = this.userService.save(userAddRequest.toEntity());
            return UserResponse.builder()
                    .idx(newUser.getIdx())
                    .id(newUser.getId())
                    .name(newUser.getName())
                    .auth(newUser.getAuth())
                    .build();
        } catch (Exception e) {
            throw new ParameterException();
        }
    }
}
