package com.example.todoapp.controllers;

import com.example.todoapp.entities.Todo;
import com.example.todoapp.repos.TodoRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/todos")
public class TodoController {
    private TodoRepository todoRepository;

    public TodoController(TodoRepository todoRepository) {
        this.todoRepository = todoRepository;
    }

    @GetMapping()
    public List<Todo> getAllTodos(@RequestParam(required = false) Boolean done) {
        if (done != null) {
            return todoRepository.findByIsDone(done);
        }
        return todoRepository.findAll();
    }
    @PostMapping
    public Todo createTodo(@RequestBody Todo newTodo){
        return todoRepository.save(newTodo);
    }

    @GetMapping("/{todoId}")
    public Todo getOneTodo(@PathVariable Long todoId){
        return todoRepository.findById(todoId).orElse(null);

    }
    @PutMapping("/{todoId}")
    public Todo updateOneTodo(@PathVariable Long todoId, @RequestBody Todo newTodo){
        Optional<Todo> todo = todoRepository.findById(todoId);
        if(todo.isPresent()){
            Todo foundTodo =todo.get();
            foundTodo.setTitle(newTodo.getTitle());
            foundTodo.setDescription(newTodo.getDescription());
            foundTodo.setDone(newTodo.isDone());
            todoRepository.save(foundTodo);
            return foundTodo;
        }else
            return null;
    }
    @DeleteMapping("/{todoId}")
    public void deleteOneTodo(@PathVariable Long todoId){
        todoRepository.deleteById(todoId);
    }
    @PatchMapping("/{todoId}/toggle")
    public Todo toggleDone(@PathVariable Long todoId) {
        Optional<Todo> todo = todoRepository.findById(todoId);
        if(todo.isPresent()){
            Todo foundTodo =todo.get();
            foundTodo.setDone(!foundTodo.isDone());
            todoRepository.save(foundTodo);
            return foundTodo;
        }else
            return null;


    }

}
