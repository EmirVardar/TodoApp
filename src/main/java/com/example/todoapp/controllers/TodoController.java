package com.example.todoapp.controllers;

import com.example.todoapp.dto.TodoCreateRequest;
import com.example.todoapp.dto.TodoResponse;
import com.example.todoapp.dto.TodoUpdateRequest;
import com.example.todoapp.entities.Todo;
import com.example.todoapp.repos.TodoRepository;
import com.example.todoapp.services.TodoService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/todos")
public class TodoController {
    private final TodoService todoService;
    public TodoController(TodoService todoService) {
        this.todoService = todoService;
    }

    @GetMapping
    public List<TodoResponse> getAll(@RequestParam(required = false) Boolean done) {
        return todoService.getAll(done);
    }

    @GetMapping("/{id}")
    public TodoResponse getOne(@PathVariable Long id) {
        return todoService.getById(id);
    }

    @PostMapping
    public TodoResponse create(@RequestBody TodoCreateRequest req) {
        return todoService.create(req);
    }

    @PutMapping("/{id}")
    public TodoResponse update(@PathVariable Long id, @RequestBody TodoUpdateRequest req) {
        return todoService.update(id, req);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        todoService.delete(id);
    }

    @PatchMapping("/{id}/toggle")
    public TodoResponse toggle(@PathVariable Long id) {
        return todoService.toggleDone(id);
    }

}
