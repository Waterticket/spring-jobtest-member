package dev.waterticket.jobdemo.user.dto;

import dev.waterticket.jobdemo.user.domain.User;
import lombok.*;

import java.util.List;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserResponse {
    private Integer idx;
    private String id;
    private String name;
    private String auth;

    private static UserResponse toResponse(User user) {
        return UserResponse.builder()
                .idx(user.getIdx())
                .id(user.getId())
                .name(user.getName())
                .auth(user.getAuth())
                .build();
    }

    public static List<UserResponse> listOf(List<User> users) {
        return users.stream()
                .map(UserResponse::toResponse)
                .toList();
    }
}
