package dev.waterticket.jobdemo.user.controller;

import dev.waterticket.jobdemo.user.domain.User;
import dev.waterticket.jobdemo.user.dto.UserAddRequest;
import dev.waterticket.jobdemo.user.dto.UserDeleteRequest;
import dev.waterticket.jobdemo.user.dto.UserResponse;
import dev.waterticket.jobdemo.user.dto.UserUpdateNameRequest;
import dev.waterticket.jobdemo.user.service.UserHistoryService;
import dev.waterticket.jobdemo.user.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;

import java.util.List;

@RestController
@RequestMapping("/api/user")
public class UserController {
    private final UserService userService;
    private final UserHistoryService userHistoryService;

    public UserController(UserService userService, UserHistoryService userHistoryService) {
        this.userService = userService;
        this.userHistoryService = userHistoryService;
    }

    @GetMapping
    public List<UserResponse> getMemberList() {
        List<User> users = this.userService.getUserAll();
        return UserResponse.listOf(users);
    }


    @GetMapping("/idx/{idx}")
    public UserResponse getMemberByIdx(@PathVariable("idx") final Integer idx) {
        User user = this.userService.getUserByIdx(idx);
        return UserResponse.builder()
                .idx(user.getIdx())
                .id(user.getId())
                .name(user.getName())
                .auth(user.getAuth())
                .build();
    }

    @GetMapping("/id/{id}")
    public UserResponse getMemberById(@PathVariable("id") final String id) {
        User user = this.userService.getUserById(id);
        return UserResponse.builder()
                .idx(user.getIdx())
                .id(user.getId())
                .name(user.getName())
                .auth(user.getAuth())
                .build();
    }

    @GetMapping("/name/{name}")
    public List<UserResponse> getMemberByName(@PathVariable("name") final String name) {
        List<User> users = this.userService.getUserByName(name);
        return UserResponse.listOf(users);
    }

    @PostMapping
    public UserResponse insertMember(@RequestBody @Valid final UserAddRequest userAddRequest, HttpServletRequest request) {
        User newUser = this.userService.insert(userAddRequest.toEntity());
        this.userHistoryService.createUser(newUser.getIdx(), request.getRequestURI(), request.getRemoteAddr());
        return UserResponse.builder()
                .idx(newUser.getIdx())
                .id(newUser.getId())
                .name(newUser.getName())
                .auth(newUser.getAuth())
                .build();
    }

    @PutMapping
    public UserResponse updateMemberName(@RequestBody @Valid final UserUpdateNameRequest userUpdateNameRequest, HttpServletRequest request) {
        User user = this.userService.updateName(userUpdateNameRequest.getId(), userUpdateNameRequest.getName());
        this.userHistoryService.updateUser(user.getIdx(), request.getRequestURI(), request.getRemoteAddr());

        return UserResponse.builder()
                .idx(user.getIdx())
                .id(user.getId())
                .name(user.getName())
                .auth(user.getAuth())
                .build();
    }

    @DeleteMapping
    public UserResponse deleteMember(@RequestBody @Valid final UserDeleteRequest userDeleteRequest, HttpServletRequest request) {
        User user = this.userService.delete(userDeleteRequest.getId());
        this.userHistoryService.deleteUser(user.getIdx(), request.getRequestURI(), request.getRemoteAddr());

        return UserResponse.builder()
                .idx(user.getIdx())
                .id(user.getId())
                .name(user.getName())
                .auth(user.getAuth())
                .build();
    }
}
