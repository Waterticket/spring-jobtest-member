package dev.waterticket.jobdemo.user.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;

@Getter
public class UserUpdateNameRequest {
    @NotBlank
    @Size(min = 4, max = 30, message = "아이디는 4~30자로 입력해주세요")
    private String id;

    @NotBlank
    @Size(min = 2, max = 100, message = "이름은 2~100자로 입력해주세요")
    private String name;
}

