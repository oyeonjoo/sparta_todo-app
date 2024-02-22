package com.sparta.todoapp.controller;

import com.sparta.todoapp.dto.response.CreateTodoResponseDto;
import com.sparta.todoapp.dto.response.TodoResponseDto;
import com.sparta.todoapp.dto.request.TodoRequestDto;
import com.sparta.todoapp.jwt.JwtUtil;
import com.sparta.todoapp.service.TodoService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/todos")
@RequiredArgsConstructor
public class TodoController {
    private final TodoService todoService;

    @PostMapping("/create")
    public CreateTodoResponseDto createTodos(@RequestHeader(value = "Authorization") String token, @RequestBody TodoRequestDto requestDto){
        // @RequestHeader 로 가져온다
        return todoService.createTodos(token, requestDto);
    }

    @GetMapping("/inquire")
    public List<TodoResponseDto> getTodos() {
        return todoService.getTodos();
    }

    @GetMapping("/{id}/inquire")
    public TodoResponseDto getTodo(@PathVariable Long id) {
        return todoService.getTodo(id);
    }

    @PutMapping("/{id}/complete")
    public String completeTodo(@RequestHeader(value = JwtUtil.AUTHORIZATION_HEADER) String token, @PathVariable Long id) {
        return todoService.completeTodo(token, id);
    }
    @PutMapping("/{id}/update")
    public TodoResponseDto updateTodo(@RequestHeader(value = JwtUtil.AUTHORIZATION_HEADER) String token, @PathVariable Long id, @RequestBody TodoRequestDto requestDto) {
        return todoService.updateTodo(token, id, requestDto);
    }
}
