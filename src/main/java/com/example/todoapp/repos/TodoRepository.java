package com.example.todoapp.repos;

import com.example.todoapp.entities.Todo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TodoRepository extends JpaRepository<Todo,Long> {
    List<Todo> findByIsDone(boolean isDone);
}
