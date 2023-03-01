package dev.waterticket.jobdemo.user.repository;

import dev.waterticket.jobdemo.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {
    Optional<User> findByIdx(int idx);
    Optional<User> findById(String id);
    List<User> findAllByName(String name);
    List<User> findAll();
}
