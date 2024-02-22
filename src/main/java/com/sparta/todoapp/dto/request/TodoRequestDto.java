package com.sparta.todoapp.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class TodoRequestDto {
    @NotBlank(message = "제목을 필수로 입력해야 합니다.")
    private String title;

    @NotBlank(message = "내용을 필수로 입력해야 합니다.")
    private String content;

    public TodoRequestDto(String title, String content) {
        this.title = title;
        this.content = content;
    }
}
