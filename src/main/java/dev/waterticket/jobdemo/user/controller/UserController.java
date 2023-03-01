package dev.waterticket.jobdemo.user.controller;

import dev.waterticket.jobdemo.user.domain.User;
import dev.waterticket.jobdemo.user.dto.UserResponse;
import dev.waterticket.jobdemo.user.service.UserService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
