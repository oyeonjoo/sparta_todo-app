package com.sparta.todoapp.controller;

import com.sparta.todoapp.dto.response.CreateTodoResponseDto;
import com.sparta.todoapp.dto.response.TodoResponseDto;
import com.sparta.todoapp.dto.request.TodoRequestDto;
import com.sparta.todoapp.global.commonDto.ResponseDto;
import com.sparta.todoapp.global.jwt.JwtUtil;
import com.sparta.todoapp.service.TodoService;
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
    public CreateTodoResponseDto createTodos(@RequestHeader(value = "Authorization") String token, @RequestBody TodoRequestDto requestDto){
        return todoService.createTodos(token, requestDto);
    }

    @GetMapping
    public List<TodoResponseDto> getTodos() {
        return todoService.getTodos();
    }

    @GetMapping("/{id}")
    public TodoResponseDto getTodo(@PathVariable Long id) {
        return todoService.getTodo(id);
    }

    @PutMapping("/{id}/complete")
    public String completeTodo(@RequestHeader(value = JwtUtil.AUTHORIZATION_HEADER) String token, @PathVariable Long id) {
        return todoService.completeTodo(token, id);
    }
    @PutMapping("/{id}")
    public TodoResponseDto updateTodo(@RequestHeader(value = JwtUtil.AUTHORIZATION_HEADER) String token, @PathVariable Long id, @RequestBody TodoRequestDto requestDto) {
        return todoService.updateTodo(token, id, requestDto);
    }
}
