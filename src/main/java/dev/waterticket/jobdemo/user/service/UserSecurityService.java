package dev.waterticket.jobdemo.user.service;

import dev.waterticket.jobdemo.user.exception.UserNotFoundException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserSecurityService implements UserDetailsService {
    private final UserService userService;

    public UserSecurityService(UserService userService) {
        this.userService = userService;
    }

    @Override
    public UserDetails loadUserByUsername(String id) throws UsernameNotFoundException {
        dev.waterticket.jobdemo.user.domain.User user;
        try {
            user = this.userService.getUserById(id);
        } catch (UserNotFoundException e) {
            throw new UsernameNotFoundException("아이디나 비밀번호를 확인해주세요.");
        }

        return User.builder()
                .username(user.getId())
                .password(user.getPassword())
                .roles(user.getAuth())
                .build();
    }
}