package com.sparta.todoapp.global.commonDto;


import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@Getter
@Setter
@Builder
public class ExceptionDto {

    private int statusCode;
    private String message;
    private HttpStatus state;

}

