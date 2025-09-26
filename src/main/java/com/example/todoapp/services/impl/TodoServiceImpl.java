package com.example.todoapp.services.impl;

import com.example.todoapp.dto.TodoCreateRequest;
import com.example.todoapp.dto.TodoResponse;
import com.example.todoapp.dto.TodoUpdateRequest;
import com.example.todoapp.entities.Todo;
import com.example.todoapp.mapper.TodoMapper;
import com.example.todoapp.repos.TodoRepository;
import com.example.todoapp.services.TodoService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional; // ← DİKKAT: Spring'in Transactional'ı

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class TodoServiceImpl implements TodoService {

    private final TodoRepository repo;
    private final TodoMapper mapper;

    public TodoServiceImpl(TodoRepository repo, TodoMapper mapper) {
        this.repo = repo;
        this.mapper = mapper;
    }

    @Override
    @Transactional(readOnly = true)
    public List<TodoResponse> getAll(Boolean done) {
        List<Todo> todos;

        if (done == null) {
            todos = repo.findAll();
        } else {
            todos = repo.findByDone(done);
        }

        List<TodoResponse> responses = mapper.toResponseList(todos);
        return responses;
    }

    @Override
    public TodoResponse create(TodoCreateRequest req) {
        // Request DTO → Entity
        Todo entity = mapper.toEntity(req);

        // DB'ye kaydet
        Todo saved = repo.save(entity);

        // Entity → Response DTO
        TodoResponse response = mapper.toResponse(saved);

        return response;
    }

    @Override
    @Transactional(readOnly = true)
    public TodoResponse getById(Long id) {
        Optional<Todo> optional = repo.findById(id);

        if (optional.isPresent()) {
            Todo entity = optional.get();
            return mapper.toResponse(entity);
        }

        // bulunamadıysa şimdilik null döner
        return null;
    }

    @Override
    @Transactional
    public TodoResponse update(Long id, TodoUpdateRequest req) {
        Optional<Todo> optional = repo.findById(id);

        if (optional.isPresent()) {
            Todo entity = optional.get();
            mapper.updateEntityFromDto(req, entity);

            Todo saved = repo.save(entity);
            return mapper.toResponse(saved);
        }

        // bulunamadıysa şimdilik null döner
        return null;
    }

    @Override
    @Transactional
    public void delete(Long id) {
        // varsa sil, yoksa hiçbir şey yapma
        if (repo.existsById(id)) {
            repo.deleteById(id);
        }
    }

    @Override
    @Transactional
    public TodoResponse toggleDone(Long id) {
        Optional<Todo> optional = repo.findById(id);

        if (optional.isPresent()) {
            Todo entity = optional.get();

            // mevcut değeri tersine çevir
            boolean current = entity.isDone();
            entity.setDone(!current);

            Todo saved = repo.save(entity);
            return mapper.toResponse(saved);
        }

        return null; // şimdilik bulunamadıysa null
    }
}
