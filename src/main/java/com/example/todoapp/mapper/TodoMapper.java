package com.example.todoapp.mapper;

import com.example.todoapp.dto.TodoCreateRequest;
import com.example.todoapp.dto.TodoResponse;
import com.example.todoapp.dto.TodoUpdateRequest;
import com.example.todoapp.entities.Todo;
import org.mapstruct.*;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.ERROR)
public interface TodoMapper {

    /* Entity -> Response (aynı isimli alanlar otomatik eşleşir, ekstra @Mapping gerekmez) */
    TodoResponse toResponse(Todo entity);

    /* CreateRequest -> Entity */
    @Mapping(target = "id",   ignore = true)     // ID DB üretir
    @Mapping(target = "done", constant = "false")// default: false (istersen ignore edip service’de set edebilirsin)
    Todo toEntity(TodoCreateRequest req);

    /* UpdateRequest -> mevcut Entity (null'ları yoksay) */
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "id", ignore = true)       // id güncellenmez
    void updateEntityFromDto(TodoUpdateRequest dto, @MappingTarget Todo entity);


    List<TodoResponse> toResponseList(List<Todo> entities);
}
