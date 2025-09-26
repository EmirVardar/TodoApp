package com.example.todoapp.services.impl;

import com.example.todoapp.dto.TodoCreateRequest;
import com.example.todoapp.dto.TodoResponse;
import com.example.todoapp.dto.TodoUpdateRequest;
import com.example.todoapp.entities.Todo;
import com.example.todoapp.exception.DuplicateException;
import com.example.todoapp.exception.NotFoundException;
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
        // 1. Aynı başlıkta bir şey var mı diye bak
        if (repo.existsByTitle(req.getTitle())) {
            // Varsa DuplicateException fırlat (409 Conflict)
            throw new DuplicateException("Todo already exists with title: " + req.getTitle());
        }

        // 2. DTO → Entity çevir
        Todo entity = mapper.toEntity(req);

        // 3. Entity'yi veritabanına kaydet
        Todo saved = repo.save(entity);

        // 4. Entity → Response DTO çevir ve geri döndür
        return mapper.toResponse(saved);
    }

    @Override
    @Transactional(readOnly = true)
    public TodoResponse getById(Long id) {
        // 1. ID ile veritabanında arama yap
        Optional<Todo> optional = repo.findById(id);

        // 2. Eğer kayıt varsa al, yoksa NotFoundException fırlat
        if (optional.isPresent()) {
            Todo todo = optional.get();
            // 3. Entity → Response DTO dönüştür
            return mapper.toResponse(todo);
        } else {
            throw new NotFoundException("Todo not found with id: " + id);
        }
    }

    @Override
    @Transactional
    public TodoResponse update(Long id, TodoUpdateRequest req) {
        // 1. ID ile veritabanında arama yap
        Optional<Todo> optional = repo.findById(id);

        if (optional.isPresent()) {
            // 2. Kayıt bulundu → entity'yi al
            Todo entity = optional.get();

            // 3. DTO → entity güncellemesi yap
            mapper.updateEntityFromDto(req, entity);

            // 4. Güncellenmiş entity'yi veritabanına kaydet
            Todo saved = repo.save(entity);

            // 5. Entity → Response DTO dönüştür
            return mapper.toResponse(saved);
        } else {
            // 6. Kayıt yoksa hata fırlat
            throw new NotFoundException("Todo not found with id: " + id);
        }
    }

    @Override
    @Transactional
    public void delete(Long id) {
        // 1. ID veritabanında var mı diye kontrol et
        boolean exists = repo.existsById(id);

        if (exists) {
            // 2. Varsa sil
            repo.deleteById(id);
        } else {
            // 3. Yoksa NotFoundException fırlat (404)
            throw new NotFoundException("Todo not found with id: " + id);
        }
    }

    @Override
    @Transactional
    public TodoResponse toggleDone(Long id) {
        // 1. ID ile veritabanında arama yap
        Optional<Todo> optional = repo.findById(id);

        if (optional.isPresent()) {
            // 2. Kayıt bulundu → entity'yi al
            Todo entity = optional.get();

            // 3. mevcut "done" değerini tersine çevir
            boolean current = entity.isDone();
            entity.setDone(!current);

            // 4. Güncellenmiş entity'yi kaydet
            Todo saved = repo.save(entity);

            // 5. Entity → Response DTO dönüştür
            return mapper.toResponse(saved);
        } else {
            // 6. Kayıt bulunamadı → NotFoundException fırlat
            throw new NotFoundException("Todo not found with id: " + id);
        }
    }

}
