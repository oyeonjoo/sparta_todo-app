package com.sparta.todoapp.dto;

import com.sparta.todoapp.entity.Todo;
import lombok.Getter;
@Getter
public class TodoDto {
    private String username;
    private String title;
    private String content;
    //private boolean isComplete;

    public TodoDto(Todo todo) {
        this.username = todo.getUser().getUsername();
        this.title = todo.getTitle();
        this.content = todo.getContent();
    }

}
