package com.sparta.todoapp.global.commonDto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ResponseDto<T> {

    private String message;
    private T data;

}