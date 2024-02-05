package com.sparta.todoapp.dto;

import lombok.Getter;

@Getter
public class SignInRequestDto {
    private String username;
    private String password;

    public SignInRequestDto(String username, String password) {
        this.username = username;
        this.password = password;
    }
}