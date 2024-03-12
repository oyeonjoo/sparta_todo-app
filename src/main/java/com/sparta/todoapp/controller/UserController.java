package com.sparta.todoapp.controller;

import com.sparta.todoapp.dto.request.SignInRequestDto;
import com.sparta.todoapp.dto.request.SignUpRequestDto;
import com.sparta.todoapp.global.jwt.JwtUtil;
import com.sparta.todoapp.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    @PostMapping("/signup")
    public ResponseEntity<String> signup(@RequestBody SignUpRequestDto requestDto) {
        userService.signup(requestDto);

        return ResponseEntity.ok("가입 성공");
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody SignInRequestDto requestDto) {

        return ResponseEntity.ok().header(JwtUtil.AUTHORIZATION_HEADER, userService.login(requestDto)).body("로그인 성공");
    }
}
