package com.sparta.todoapp.service;

import com.sparta.todoapp.dto.request.TodoRequestDto;
import com.sparta.todoapp.dto.response.CommentResponseDto;
import com.sparta.todoapp.dto.response.TodoResponseDto;
import com.sparta.todoapp.dto.response.CreateTodoResponseDto;
import com.sparta.todoapp.dto.response.TodoDetailResponseDto;
import com.sparta.todoapp.entity.Comment;
import com.sparta.todoapp.entity.Todo;
import com.sparta.todoapp.entity.User;
import com.sparta.todoapp.global.jwt.JwtUtil;
import com.sparta.todoapp.repository.CommentRepository;
import com.sparta.todoapp.repository.TodoRepository;
import com.sparta.todoapp.repository.UserRepository;
import java.util.ArrayList;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class TodoService {

    private final TodoRepository todoRepository;
    private final UserRepository userRepository;
    private final CommentRepository commentRepository;
    private final JwtUtil jwtUtil;

    public CreateTodoResponseDto createTodos(String tokenValue, TodoRequestDto requestDto) {
        String username = jwtUtil.getSubject(tokenValue);

        User user = userRepository.findByUsername(username).orElseThrow(
            () -> new NoSuchElementException("해당 유저가 없습니다.")
        );

        Todo todo = new Todo(requestDto.getTitle(), requestDto.getContent(), user);

        Todo saveTodo = todoRepository.save(todo);

        return new CreateTodoResponseDto(username, saveTodo.getTitle(), saveTodo.getContent());
    }

    public List<TodoResponseDto> getTodos() {

        return todoRepository.findAllByOrderByCreatedAtDesc().stream().map(TodoResponseDto::new)
            .toList();
    }

    public TodoDetailResponseDto getTodo(Long id) {
        Todo todo = findTodoBy(id);

        List<CommentResponseDto> commentResponseDtoList =
            commentRepository.findAllByTodo(todo).stream().map(
                comment -> new CommentResponseDto(
                    comment.getUser().getUsername(),
                    comment.getContent()
                )
            ).toList();

        return new TodoDetailResponseDto(todo, commentResponseDtoList);
    }

    @Transactional
    public TodoResponseDto updateTodo(String tokenValue, Long id, TodoRequestDto requestDto) {
        Todo todo = findTodo(tokenValue, id);

        todo.update(requestDto);

        return new TodoResponseDto(todo);
    }

    @Transactional
    public String completeTodo(String tokenValue, Long id) {
        Todo todo = findTodo(tokenValue, id);

        todo.updateCompleteStatus();

        return "완료되었습니다";
    }

    private Todo findTodoBy(Long id) {
        return todoRepository.findById(id).orElseThrow(
            () -> new NoSuchElementException("선택한 일정이 존재하지 않습니다.")
        );
    }

    private Todo findTodo(String tokenValue, Long id) {
        String username = jwtUtil.getSubject(tokenValue);
        Todo todo = findTodoBy(id);

        usernameMatchValidate(todo, username);

        return todo;
    }

    private void usernameMatchValidate(Todo todo, String username) {
        if (todo.isNotUsernameMatch(username)) {
            throw new AccessDeniedException("작성한 글만 수정 가능합니다.");
        }
    }

}
