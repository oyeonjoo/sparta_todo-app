package com.sparta.todoapp.service;

import com.sparta.todoapp.dto.request.CommentRequestDto;
import com.sparta.todoapp.dto.response.CommentResponseDto;
import com.sparta.todoapp.entity.Comment;
import com.sparta.todoapp.entity.Todo;
import com.sparta.todoapp.entity.User;
import com.sparta.todoapp.global.jwt.JwtUtil;
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
@Transactional
public class CommentService {

    private final CommentRepository commentRepository;
    private final TodoRepository todoRepository;
    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;


    public CommentResponseDto createComment(String tokenValue, Long todoId,
        CommentRequestDto requestDto) {
        String username = jwtUtil.getSubject(tokenValue);

        User user = findUserBy(username);

        Todo todo = findTodoBy(todoId);

        Comment comment = new Comment(todo, requestDto.getContent(), user);

        Comment saveComment = commentRepository.save(comment);

        return new CommentResponseDto(username, saveComment.getContent());
    }

    public CommentResponseDto updateComment(String tokenValue, Long todoId, Long commentId,
        CommentRequestDto requestDto) {
        String username = jwtUtil.getSubject(tokenValue);

        User user = findUserBy(username);

        Todo todo = findTodoBy(todoId);

        Comment comment = findCommentBy(commentId);

        commentExist(todo, comment);

        userMatchValidate(comment, user);

        comment.update(requestDto);

        return new CommentResponseDto(comment);
    }

    public String deleteComment(String tokenValue, Long todoId, Long commentId) {
        String username = jwtUtil.getSubject(tokenValue);

        User user = findUserBy(username);

        Todo todo = findTodoBy(todoId);

        Comment comment = findCommentBy(commentId);

        commentExist(todo, comment);

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
