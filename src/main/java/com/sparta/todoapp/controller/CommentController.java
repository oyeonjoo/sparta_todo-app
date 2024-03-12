package com.sparta.todoapp.controller;

import com.sparta.todoapp.dto.request.CommentRequestDto;
import com.sparta.todoapp.dto.response.CommentResponseDto;
import com.sparta.todoapp.global.commonDto.ResponseDto;
import com.sparta.todoapp.global.jwt.JwtUtil;
import com.sparta.todoapp.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/todos/{todoId}/comments")
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    @PostMapping
    public ResponseEntity<ResponseDto<CommentResponseDto>> createComment(
        @RequestHeader(value = JwtUtil.AUTHORIZATION_HEADER) String token,
        @PathVariable Long todoId,
        @RequestBody CommentRequestDto requestDto) {

        return ResponseEntity.ok()
            .body(ResponseDto.<CommentResponseDto>builder()
                .message("작성 성공")
                .data(commentService.createComment(token, todoId, requestDto))
                .build());
    }

    @PutMapping("/{commentId}")
    public ResponseEntity<ResponseDto<CommentResponseDto>> updateComment(
        @RequestHeader(value = JwtUtil.AUTHORIZATION_HEADER) String token,
        @PathVariable Long todoId,
        @PathVariable Long commentId,
        @RequestBody CommentRequestDto requestDto) {

        return ResponseEntity.ok()
            .body(ResponseDto.<CommentResponseDto>builder()
                .message("수정 성공")
                .data(commentService.updateComment(token, todoId, commentId, requestDto))
                .build());

    }

    @DeleteMapping("/{commentId}")
    public ResponseEntity<String> deleteComment(
        @RequestHeader(value = JwtUtil.AUTHORIZATION_HEADER) String token,
        @PathVariable Long todoId,
        @PathVariable Long commentId) {

        return ResponseEntity.ok()
            .body(commentService.deleteComment(token, todoId, commentId));
    }
}
