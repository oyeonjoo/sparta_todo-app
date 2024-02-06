package com.sparta.todoapp.dto.request;

import lombok.Getter;

@Getter
public class CommentRequestDto {
    private String username;
    private String content;

    public CommentRequestDto(String username, String content) {
        this.username = username;
        this.content = content;
    }
}
