package com.example.todoapp.dto;

import lombok.Data;

@Data
public class TodoResponse {
    private Long id;
    private String title;
    private String description;
    private boolean done;
}
