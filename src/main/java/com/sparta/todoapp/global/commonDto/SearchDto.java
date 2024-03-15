package com.sparta.todoapp.global.commonDto;

import lombok.Data;

@Data
public class SearchDto {

    private String title;
    private String content;
    private String username;

    public SearchDto(String title, String content, String username) {
        this.title = title;
        this.content = content;
        this.username = username;
    }
}
