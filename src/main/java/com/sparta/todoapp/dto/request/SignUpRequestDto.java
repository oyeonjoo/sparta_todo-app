package com.sparta.todoapp.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;

@Getter
public class SignUpRequestDto {
    @Size(min=4, max=10)
    @Pattern(regexp = "^[a-z0-9]*$")
    @NotBlank // 공백도 허용하지 않는다
    private String username;

    @Size(min=8, max=15)
    @Pattern(regexp = "^[a-zA-Z0-9]*$")
    @NotBlank
    private String password;

    public SignUpRequestDto(String username, String password) {
        this.username = username;
        this.password = password;
    }
}
