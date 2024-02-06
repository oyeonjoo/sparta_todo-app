package com.sparta.todoapp.controller;

import com.sparta.todoapp.dto.request.CommentRequestDto;
import com.sparta.todoapp.dto.response.CommentResponseDto;
import com.sparta.todoapp.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/todos/{todoId}/comments")
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;
    @PostMapping("/create")
    public CommentResponseDto createComment(@RequestHeader(value = "Authorization") String token, @PathVariable Long todoId, @RequestBody CommentRequestDto requestDto) {
        return commentService.createComment(token, todoId, requestDto);
    }

    @PutMapping("/update/{commentId}")
    public CommentResponseDto updateComment(@RequestHeader(value = "Authorization") String token, @PathVariable Long todoId, @PathVariable Long commentId, @RequestBody CommentRequestDto requestDto) {
        return commentService.updateComment(token, todoId, commentId, requestDto);
    }

    @DeleteMapping("/delete/{commentId}")
    public String deleteComment(@RequestHeader(value = "Authorization") String token, @PathVariable Long todoId, @PathVariable Long commentId) {
        return commentService.deleteComment(token, todoId, commentId);
    }
}
