package com.example.todoapp.dto;

import lombok.Data;

@Data
public class TodoUpdateRequest {
    private String title;       // null ise güncellenmez
    private String description; // null ise güncellenmez
    private Boolean done;       // null ise güncellenmez
}
