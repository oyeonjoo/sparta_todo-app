package com.sparta.todoapp.repository;

import com.sparta.todoapp.entity.Comment;
import com.sparta.todoapp.entity.Todo;
import com.sparta.todoapp.entity.User;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CommentRepository extends JpaRepository<Comment, Long> {

    List<Comment> findAllByTodo(Todo todo);
}
