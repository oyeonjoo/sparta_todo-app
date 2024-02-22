package com.sparta.todoapp.service;

import com.sparta.todoapp.dto.request.TodoRequestDto;
import com.sparta.todoapp.dto.response.CreateTodoResponseDto;
import com.sparta.todoapp.dto.response.TodoResponseDto;
import com.sparta.todoapp.entity.Todo;
import com.sparta.todoapp.entity.User;
import com.sparta.todoapp.jwt.JwtUtil;
import com.sparta.todoapp.repository.TodoRepository;
import com.sparta.todoapp.repository.UserRepository;
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
    private final JwtUtil jwtUtil;

    public CreateTodoResponseDto createTodos(String tokenValue, TodoRequestDto requestDto) {
        // user 정보 반환(토큰 확인, 검증,user 정보 가져오기)
        String username = jwtUtil.getSubject(tokenValue);

        // 해당 user 찾기
        User user = userRepository.findByUsername(username).orElseThrow(
                () -> new NoSuchElementException("해당 유저가 없습니다.")
        );

        // RequestDto -> Entity
        Todo todo = new Todo(requestDto.getTitle(), requestDto.getContent(), user);

        // DB 저장
        Todo saveTodo = todoRepository.save(todo);

        // Entity -> ResponseDto

        return new CreateTodoResponseDto(username, saveTodo.getTitle(), saveTodo.getContent());
    }

    public List<TodoResponseDto> getTodos() {

        return todoRepository.findAllByOrderByCreatedAtDesc().stream().map(TodoResponseDto::new).toList();
    }

    public TodoResponseDto getTodo(Long id) {
        Todo todo = findTodoBy(id);

        return new TodoResponseDto(todo);
    }

    @Transactional
    public TodoResponseDto updateTodo(String tokenValue, Long id, TodoRequestDto requestDto) {
        // user 정보 반환(토큰 확인, 검증,user 정보 가져오기)
        String username = jwtUtil.getSubject(tokenValue);

        // 해당 일정이 DB에 존재하는지 확인
        Todo todo = findTodoBy(id);

        // 토큰 검사 후, 유효한 토큰이면 본인이 작성한 게시글 수정 가능
        usernameMatchValidate(todo, username);

        todo.update(requestDto);

        return new TodoResponseDto(todo);
    }

    @Transactional
    public String completeTodo(String tokenValue, Long id) {
        // user 정보 반환(토큰 확인, 검증,user 정보 가져오기)
        String username = jwtUtil.getSubject(tokenValue);

        // 해당 일정이 DB에 존재하는지 확인
        Todo todo = findTodoBy(id);

        // 토큰 검사 후, 유효한 토큰이면 본인이 작성한 게시글 수정 가능
        usernameMatchValidate(todo, username);

        todo.updateCompleteStatus();

        return "완료되었습니다";
    }

    private Todo findTodoBy(Long id) {
        return todoRepository.findById(id).orElseThrow(
                () -> new NoSuchElementException("선택한 일정이 존재하지 않습니다.")
        );
    }

    private void usernameMatchValidate(Todo todo, String username) {
        if(todo.isNotUsernameMatch(username)) {
            throw new AccessDeniedException("작성한 글만 수정 가능합니다.");
        }
    }

}
