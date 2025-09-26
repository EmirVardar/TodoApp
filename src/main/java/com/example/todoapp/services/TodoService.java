package com.example.todoapp.services;

import com.example.todoapp.dto.TodoCreateRequest;
import com.example.todoapp.dto.TodoResponse;
import com.example.todoapp.dto.TodoUpdateRequest;

import java.util.List;

public interface TodoService {
    List<TodoResponse> getAll(Boolean done);
    TodoResponse getById(Long id);
    TodoResponse create(TodoCreateRequest req);
    TodoResponse update(Long id, TodoUpdateRequest req);
    void delete(Long id);
    TodoResponse toggleDone(Long id);
}
