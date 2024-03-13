package com.sparta.todoapp.dto.response;

import com.sparta.todoapp.entity.Todo;
import java.util.List;
import lombok.Getter;

@Getter
public class TodoDetailResponseDto {

    private String username;
    private String title;
    private String content;
    private boolean isComplete;

    private List<CommentResponseDto> commentResponseDtoList;

    public TodoDetailResponseDto(Todo todo,
        List<CommentResponseDto> commentResponseDtoList) {
        this.username = todo.getUser().getUsername();
        this.title = todo.getTitle();
        this.content = todo.getContent();
        this.isComplete = todo.isComplete();
        this.commentResponseDtoList = commentResponseDtoList;
    }
}
