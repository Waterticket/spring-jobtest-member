package dev.waterticket.jobdemo.user.service;

import dev.waterticket.jobdemo.user.domain.User;
import dev.waterticket.jobdemo.user.exception.UserNotFoundException;
import dev.waterticket.jobdemo.user.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User getUserByIdx(int idx) {
        return this.userRepository.findByIdx(idx)
                .orElseThrow(UserNotFoundException::new);
    }

    public List<User> getUserAll() {
        List<User> users = this.userRepository.findAll();
        if (users == null || users.isEmpty()) {
            throw new UserNotFoundException();
        }

        return users;
    }
}
