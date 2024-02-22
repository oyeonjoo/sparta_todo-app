package com.sparta.todoapp.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;

@Getter
public class SignUpRequestDto {
    @Size(min=4, max=10)
    @Pattern(regexp = "^[a-z0-9]*$")
    @NotBlank(message = "이름은 필수로 입력해야 합니다.") // 공백도 허용하지 않는다
    private String username;

    @Size(min=8, max=15)
    @Pattern(regexp = "^[a-zA-Z0-9]*$")
    @NotBlank(message = "비밀번호는 필수로 입력해야 합니다.")
    private String password;

    public SignUpRequestDto(String username, String password) {
        this.username = username;
        this.password = password;
    }
}
