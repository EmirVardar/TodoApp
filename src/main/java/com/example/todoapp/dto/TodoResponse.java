package com.example.todoapp.dto;

import lombok.Data;

public record TodoResponse(
        Long id,
        String title,
        String description,
        boolean done
) {}
