package com.sparta.todoapp.controller;

import com.sparta.todoapp.dto.request.CommentRequestDto;
import com.sparta.todoapp.dto.response.CommentResponseDto;
import com.sparta.todoapp.global.commonDto.ResponseDto;
import com.sparta.todoapp.global.jwt.JwtUtil;
import com.sparta.todoapp.service.CommentService;
import com.sparta.todoapp.userDetails.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/todos/{todoId}/comments")
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    @PostMapping
    public ResponseEntity<ResponseDto<CommentResponseDto>> createComment(
        @AuthenticationPrincipal UserDetailsImpl userDetails,
        @PathVariable Long todoId,
        @RequestBody CommentRequestDto requestDto) {

        return ResponseEntity.ok()
            .body(ResponseDto.<CommentResponseDto>builder()
                .message("작성 성공")
                .data(commentService.createComment(userDetails.getUsername(), todoId, requestDto))
                .build());
    }

    @PutMapping("/{commentId}")
    public ResponseEntity<ResponseDto<CommentResponseDto>> updateComment(
        @AuthenticationPrincipal UserDetailsImpl userDetails,
        @PathVariable Long todoId,
        @PathVariable Long commentId,
        @RequestBody CommentRequestDto requestDto) {

        return ResponseEntity.ok()
            .body(ResponseDto.<CommentResponseDto>builder()
                .message("수정 성공")
                .data(commentService.updateComment(userDetails.getUsername(), todoId, commentId, requestDto))
                .build());

    }

    @DeleteMapping("/{commentId}")
    public ResponseEntity<String> deleteComment(
        @AuthenticationPrincipal UserDetailsImpl userDetails,

        @PathVariable Long todoId,
        @PathVariable Long commentId) {

        return ResponseEntity.ok()
            .body(commentService.deleteComment(userDetails.getUsername(), todoId, commentId));
    }
}
