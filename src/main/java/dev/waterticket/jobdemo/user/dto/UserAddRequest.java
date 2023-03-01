package dev.waterticket.jobdemo.user.dto;


import dev.waterticket.jobdemo.user.domain.User;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserAddRequest {
    @NotBlank
    @Size(min = 4, max = 30, message = "아이디는 4~30자로 입력해주세요")
    private String id;

    @NotBlank
    @Size(min = 2, max = 100, message = "이름은 2~100자로 입력해주세요")
    private String name;

    @NotBlank
    @Size(min = 4, max = 20, message = "권한은 4~20자로 입력해주세요")
    private String auth = "USER";

    private String password = "";

    public User toEntity() {
        return User.builder()
                .id(id)
                .name(name)
                .auth(auth)
                .password(password)
                .build();
    }
}