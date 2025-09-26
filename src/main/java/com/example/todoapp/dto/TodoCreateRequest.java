package com.example.todoapp.dto;

import lombok.Data;

@Data
public class TodoCreateRequest {
    private String title;
    private String description;
}
