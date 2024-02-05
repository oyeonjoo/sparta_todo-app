package com.sparta.todoapp.controller;

import com.sparta.todoapp.dto.CreateTodoResponseDto;
import com.sparta.todoapp.dto.TodoDto;
import com.sparta.todoapp.dto.TodoRequestDto;
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
    public List<TodoDto> getTodos() {
        return todoService.getTodos();
    }

    @GetMapping("/inquire/{id}")
    public TodoDto getTodo( @PathVariable Long id) {
        return todoService.getTodo(id);
    }

    @PutMapping("/update/{id}")
    public TodoDto updateTodo(@RequestHeader(value = "Authorization") String token, @PathVariable Long id, @RequestBody TodoRequestDto requestDto) {
        return todoService.updateTodo(token, id, requestDto);
    }
}
