package com.sparta.todoapp.dto.response;

import com.sparta.todoapp.entity.Todo;
import lombok.Getter;
@Getter
public class TodoResponseDto {
    private String username;
    private String title;
    private String content;
    //private boolean isComplete;

    public TodoResponseDto(Todo todo) {
        this.username = todo.getUser().getUsername();
        this.title = todo.getTitle();
        this.content = todo.getContent();
    }

}
