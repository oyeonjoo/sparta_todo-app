package com.sparta.todoapp.controller;

import com.sparta.todoapp.dto.request.TodoRequestDto;
import com.sparta.todoapp.dto.response.CreateTodoResponseDto;
import com.sparta.todoapp.dto.response.TodoDetailResponseDto;
import com.sparta.todoapp.dto.response.TodoResponseDto;
import com.sparta.todoapp.global.commonDto.ResponseDto;
import com.sparta.todoapp.global.jwt.JwtUtil;
import com.sparta.todoapp.service.TodoService;
import com.sparta.todoapp.userDetails.UserDetailsImpl;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/todos")
@RequiredArgsConstructor
public class TodoController {

    private final TodoService todoService;

    @PostMapping
    public ResponseEntity<ResponseDto<CreateTodoResponseDto>> createTodos(
        @AuthenticationPrincipal UserDetailsImpl userDetails,
        @RequestBody @Valid TodoRequestDto requestDto) {

        CreateTodoResponseDto createTodoResponseDto = todoService.createTodos(
            userDetails.getUsername(), requestDto);

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
        @AuthenticationPrincipal UserDetailsImpl userDetails,
        @PathVariable Long id) {

        return ResponseEntity.ok()
            .body(todoService.completeTodo(userDetails.getUsername(), id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResponseDto<TodoResponseDto>> updateTodo(
        @AuthenticationPrincipal UserDetailsImpl userDetails,
        @PathVariable Long id,
        @RequestBody @Valid TodoRequestDto requestDto) {

        return ResponseEntity.ok()
            .body(ResponseDto.<TodoResponseDto>builder()
                .message("수정 성공")
                .data(todoService.updateTodo(userDetails.getUsername(), id, requestDto))
                .build());
    }
}
