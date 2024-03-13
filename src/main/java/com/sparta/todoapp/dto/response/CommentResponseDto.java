package com.sparta.todoapp.dto.response;

import com.sparta.todoapp.entity.Comment;
import lombok.Getter;

@Getter
public class CommentResponseDto {

    private String username;
    private String content;

    public CommentResponseDto(Comment comment) {
        this.username = comment.getUser().getUsername();
        this.content = comment.getContent();
    }

    public CommentResponseDto(String username, String content) {
        this.username = username;
        this.content = content;
    }
}
