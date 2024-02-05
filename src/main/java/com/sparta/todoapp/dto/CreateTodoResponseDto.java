package com.sparta.todoapp.dto;

import com.sparta.todoapp.entity.Todo;
import lombok.Getter;

@Getter
public class CreateTodoResponseDto {
    private String username;
    private String title;
    private String content;

    public CreateTodoResponseDto(String username, String title, String content) {
        this.username = username;
        this.title = title;
        this.content = content;
    }
}
