package com.sparta.todoapp.service;

import com.sparta.todoapp.dto.request.SignInRequestDto;
import com.sparta.todoapp.dto.request.SignUpRequestDto;
import com.sparta.todoapp.entity.User;
import com.sparta.todoapp.jwt.JwtUtil;
import com.sparta.todoapp.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    public void signup(SignUpRequestDto requestDto) {
        String username = requestDto.getUsername();
        String password = passwordEncoder.encode(requestDto.getPassword());

        // 회원 중복 확인
        Optional<User> checkUsername = userRepository.findByUsername(username);

        if (checkUsername.isPresent()) {
            throw new DuplicateKeyException("중복된 사용자가 존재합니다.");
        }

        // 사용자 등록
        User user = new User(username, password);
        userRepository.save(user);
    }

    public String login(SignInRequestDto requestDto) {
        String username = requestDto.getUsername();
        String password = requestDto.getPassword();

        // 사용자 확인
        User user = userRepository.findByUsername(username).orElseThrow(
                () -> new NoSuchElementException("등록된 사용자가 없습니다.")
        );

        // 비밀번호 확인
        passwordMatchValidate(password, user);

        return jwtUtil.createToken(username);
    }

    private void passwordMatchValidate(String password, User user) {
        if(user.isNotPasswordMatch(password, passwordEncoder)) {
            throw new BadCredentialsException("비밀번호가 일치하지 않습니다.");
        }
    }
}
