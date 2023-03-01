package dev.waterticket.jobdemo.user.service;

import dev.waterticket.jobdemo.user.domain.User;
import dev.waterticket.jobdemo.user.exception.SameIDExistsException;
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

    public User getUserById(String id) {
        return this.userRepository.findById(id)
                .orElseThrow(UserNotFoundException::new);
    }

    public List<User> getUserByName(String name) {
        List<User> users = this.userRepository.findAllByName(name);
        if (users == null || users.isEmpty()) {
            throw new UserNotFoundException();
        }

        return users;
    }

    public User insert(User user) throws SameIDExistsException {
        try {
            User oldUser = this.getUserById(user.getId());
            if (oldUser != null) {
                throw new SameIDExistsException();
            }
        } catch (UserNotFoundException e) {
            // do nothing
        }

        return this.userRepository.save(user);
    }

    public User updateName(String id, String name) throws UserNotFoundException {
        User user = this.getUserById(id);
        user.setName(name);
        return this.userRepository.save(user);
    }

    public User delete(String id) throws UserNotFoundException {
        User user = this.getUserById(id);
        this.userRepository.delete(user);
        return user;
    }
}
