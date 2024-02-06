package com.sparta.todoapp.entity;

import com.sparta.todoapp.dto.request.CommentRequestDto;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Table(name = "comments")
@NoArgsConstructor
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "username", nullable = false)
    private String username;

    @Column(name = "content", nullable = false)
    private String content;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "todo_id")
    private Todo todo;

    public Comment(Todo todo, String username, String content, User user) {
        this.todo = todo;
        this.username = username;
        this.content = content;
        this.user = user;
    }

    public void update(CommentRequestDto requestDto) {
        this.username = requestDto.getUsername();
        this.content = requestDto.getContent();
    }
}