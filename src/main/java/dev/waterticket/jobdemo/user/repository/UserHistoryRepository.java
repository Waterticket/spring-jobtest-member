package dev.waterticket.jobdemo.user.repository;

import dev.waterticket.jobdemo.user.domain.UserHistory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserHistoryRepository extends JpaRepository<UserHistory, Integer> {
}
