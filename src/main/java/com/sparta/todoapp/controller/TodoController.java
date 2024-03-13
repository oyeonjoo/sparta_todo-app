package com.sparta.todoapp.controller;

import com.sparta.todoapp.dto.response.TodoResponseDto;
import com.sparta.todoapp.dto.response.CreateTodoResponseDto;
import com.sparta.todoapp.dto.response.TodoDetailResponseDto;
import com.sparta.todoapp.dto.request.TodoRequestDto;
import com.sparta.todoapp.global.commonDto.ResponseDto;
import com.sparta.todoapp.global.jwt.JwtUtil;
import com.sparta.todoapp.service.TodoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/todos")
@RequiredArgsConstructor
public class TodoController {

    private final TodoService todoService;

    @PostMapping
    public ResponseEntity<ResponseDto<CreateTodoResponseDto>> createTodos(
        @RequestHeader(value = JwtUtil.AUTHORIZATION_HEADER) String token,
        @RequestBody @Valid TodoRequestDto requestDto) {

        CreateTodoResponseDto createTodoResponseDto = todoService.createTodos(token, requestDto);

        return ResponseEntity.ok()
            .body(ResponseDto.<CreateTodoResponseDto>builder()
                .message("작성 성공")
                .data(createTodoResponseDto)
                .build());
    }

    @GetMapping
    public ResponseEntity<ResponseDto<List<TodoResponseDto>>> getTodos() {

        return ResponseEntity.ok()
            .body(ResponseDto.<List<TodoResponseDto>>builder()
                .data(todoService.getTodos())
                .build());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseDto<TodoDetailResponseDto>> getTodo(@PathVariable Long id) {

        return ResponseEntity.ok()
            .body(ResponseDto.<TodoDetailResponseDto>builder()
                .data(todoService.getTodo(id))
                .build());
    }

    @PutMapping("/{id}/complete")
    public ResponseEntity<String> completeTodo(
        @RequestHeader(value = JwtUtil.AUTHORIZATION_HEADER) String token,
        @PathVariable Long id) {

        return ResponseEntity.ok()
            .body(todoService.completeTodo(token, id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResponseDto<TodoResponseDto>> updateTodo(
        @RequestHeader(value = JwtUtil.AUTHORIZATION_HEADER) String token,
        @PathVariable Long id,
        @RequestBody @Valid TodoRequestDto requestDto) {

        return ResponseEntity.ok()
            .body(ResponseDto.<TodoResponseDto>builder()
                .message("수정 성공")
                .data(todoService.updateTodo(token, id, requestDto))
                .build());
    }
}
