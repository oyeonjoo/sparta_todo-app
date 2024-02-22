package com.sparta.todoapp.service;

import com.sparta.todoapp.dto.request.CommentRequestDto;
import com.sparta.todoapp.dto.response.CommentResponseDto;
import com.sparta.todoapp.entity.Comment;
import com.sparta.todoapp.entity.Todo;
import com.sparta.todoapp.entity.User;
import com.sparta.todoapp.jwt.JwtUtil;
import com.sparta.todoapp.repository.CommentRepository;
import com.sparta.todoapp.repository.TodoRepository;
import com.sparta.todoapp.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class CommentService {
    private final CommentRepository commentRepository;
    private final TodoRepository todoRepository;
    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;


    public CommentResponseDto createComment(String tokenValue, Long todoId, CommentRequestDto requestDto) {
        // user 정보 반환(토큰 확인, 검증,user 정보 가져오기)
        String username = jwtUtil.getSubject(tokenValue);

        // 해당 user 찾기
        User user = findUserBy(username);

        // 해당 일정이 DB에 존재하는지 확인
        Todo todo = findTodoBy(todoId);

        // RequestDto -> Entity
        Comment comment = new Comment(todo, requestDto.getContent(), user);

        // DB 저장
        Comment saveComment = commentRepository.save(comment);

        // Entity -> ResponseDto

        return new CommentResponseDto(username, saveComment.getContent());
    }

    @Transactional
    public CommentResponseDto updateComment(String tokenValue, Long todoId, Long commentId, CommentRequestDto requestDto) {
        // user 정보 반환(토큰 확인, 검증,user 정보 가져오기)
        String username = jwtUtil.getSubject(tokenValue);

        // 해당 user 찾기
        User user = findUserBy(username);

        // 해당 일정이 DB에 존재하는지 확인
        Todo todo = findTodoBy(todoId);

        // 해당 댓글이 DB에 존재하는지 확인
        Comment comment = findCommentBy(commentId);

        // todo 에 해당 댓글이 존재하는지 확인
        commentExist(todo, comment);

        // 작성한 댓글이 일치하는지 확인
        userMatchValidate(comment, user);

        comment.update(requestDto);

        return new CommentResponseDto(comment);
    }

    public String deleteComment(String tokenValue, Long todoId, Long commentId) {
        // user 정보 반환(토큰 확인, 검증,user 정보 가져오기)
        String username = jwtUtil.getSubject(tokenValue);

        // 해당 user 찾기
        User user = findUserBy(username);

        // 해당 일정이 DB에 존재하는지 확인
        Todo todo = findTodoBy(todoId);

        // 해당 댓글이 DB에 존재하는지 확인
        Comment comment = findCommentBy(commentId);

        // todo 에 해당 댓글이 존재하는지 확인
        commentExist(todo, comment);

        // 작성한 댓글이 일치하는지 확인
        userMatchValidate(comment, user);

        commentRepository.delete(comment);

        return "삭제 성공!";
    }

    private User findUserBy(String username) {
        return userRepository.findByUsername(username).orElseThrow(
                () -> new NoSuchElementException("해당 유저가 없습니다.")
        );
    }

    private Todo findTodoBy(Long todoId) {
        return todoRepository.findById(todoId).orElseThrow(
                () -> new NoSuchElementException("선택한 일정이 존재하지 않습니다.")
        );
    }

    private Comment findCommentBy(Long commentId) {
        return commentRepository.findById(commentId).orElseThrow(
                () -> new NoSuchElementException("선택한 댓글이 존재하지 않습니다.")
        );
    }

    private void commentExist(Todo todo, Comment comment) {
        if (comment.isNotTodoMatch(todo)) {
            throw new NoSuchElementException("게시글에 댓글이 존재하지 않습니다.");
        }
    }

    private void userMatchValidate(Comment comment, User user) {
        if (comment.isNotUserMatch(user)) {
            throw new AccessDeniedException("작성한 댓글만 수정 가능합니다.");
        }
    }
}
