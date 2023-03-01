package dev.waterticket.jobdemo.user.dto;

import lombok.*;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserResponse {
    private Integer idx;
    private String id;
    private String name;
    private String auth;
}
