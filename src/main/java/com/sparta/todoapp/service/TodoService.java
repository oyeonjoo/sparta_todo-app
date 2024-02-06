package com.sparta.todoapp.service;

import com.sparta.todoapp.dto.response.CreateTodoResponseDto;
import com.sparta.todoapp.dto.response.TodoResponseDto;
import com.sparta.todoapp.dto.request.TodoRequestDto;
import com.sparta.todoapp.entity.Todo;
import com.sparta.todoapp.entity.User;
import com.sparta.todoapp.jwt.JwtUtil;
import com.sparta.todoapp.repository.TodoRepository;
import com.sparta.todoapp.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TodoService {
    private final TodoRepository todoRepository;
    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;

    public CreateTodoResponseDto createTodos(String tokenValue, TodoRequestDto requestDto) {
        // user 정보 반환(토큰 확인, 검증,user 정보 가져오기)
        String username = jwtUtil.getSubject(tokenValue);

        // 해당 user 찾기
        User user = userRepository.findByUsername(username).orElseThrow(
                () -> new IllegalArgumentException("해당 유저가 없습니다.")
        );

        // RequestDto -> Entity
        Todo todo = new Todo(requestDto.getTitle(), requestDto.getContent(), user);

        // DB 저장
        Todo saveTodo = todoRepository.save(todo);

        // Entity -> ResponseDto
        CreateTodoResponseDto createTodoResponseDto = new CreateTodoResponseDto(username, saveTodo.getTitle(), saveTodo.getContent());

        return createTodoResponseDto;
    }

    public List<TodoResponseDto> getTodos() {
        List<TodoResponseDto> todos = todoRepository.findAllByOrderByCreatedAtDesc().stream().map(TodoResponseDto::new).toList();

        return todos;
    }

    public TodoResponseDto getTodo(Long id) {
        Todo todo = todoRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("선택한 일정이 존재하지 않습니다.")
        );

        return new TodoResponseDto(todo);
    }

    @Transactional
    public TodoResponseDto updateTodo(String tokenValue, Long id, TodoRequestDto requestDto) {
        // user 정보 반환(토큰 확인, 검증,user 정보 가져오기)
        String username = jwtUtil.getSubject(tokenValue);

        // 해당 일정이 DB에 존재하는지 확인
        Todo todo = todoRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("선택한 일정이 존재하지 않습니다.")
        );

        // 토큰 검사 후, 유효한 토큰이면 본인이 작성한 게시글 수정 가능
        if(!todo.getUser().getUsername().equals(username)) {
            throw new IllegalArgumentException("작성한 글만 수정 가능합니다.");
        }

        todo.update(requestDto);

        return new TodoResponseDto(todo);
    }

    public String completeTodo(String tokenValue, Long id) {
        // user 정보 반환(토큰 확인, 검증,user 정보 가져오기)
        String username = jwtUtil.getSubject(tokenValue);

        // 해당 일정이 DB에 존재하는지 확인
        Todo todo = todoRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("선택한 일정이 존재하지 않습니다.")
        );

        // 토큰 검사 후, 유효한 토큰이면 본인이 작성한 게시글 수정 가능
        if(!todo.getUser().getUsername().equals(username)) {
            throw new IllegalArgumentException("작성한 글만 수정 가능합니다.");
        }

        todo.updateCompleteStatus();

        return "완료되었습니다";
    }
}
