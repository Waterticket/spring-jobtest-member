package dev.waterticket.jobdemo.user.service;

import dev.waterticket.jobdemo.user.domain.UserHistory;
import dev.waterticket.jobdemo.user.repository.UserHistoryRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Service
public class UserHistoryService {
    private final UserHistoryRepository userHistoryRepository;

    public UserHistoryService(UserHistoryRepository userHistoryRepository) {
        this.userHistoryRepository = userHistoryRepository;
    }

    public void createUser(Integer userIdx, String url, String ip) {
        this.save(userIdx, url, ip, "C");
    }

    public void updateUser(Integer userIdx, String url, String ip) {
        this.save(userIdx, url, ip, "U");
    }

    public void deleteUser(Integer userIdx, String url, String ip) {
        this.save(userIdx, url, ip, "D");
    }

    private void save(Integer userIdx, String url, String ip, String actionType) {
        LocalDateTime now = LocalDateTime.now();
        UserHistory history = UserHistory.builder()
                .url(url)
                .actionType(actionType)
                .userIdx(userIdx)
                .ip(ip)
                .createdAt(now.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")))
                .build();

        this.userHistoryRepository.save(history);
    }
}
